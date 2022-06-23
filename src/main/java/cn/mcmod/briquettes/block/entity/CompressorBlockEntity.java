package cn.mcmod.briquettes.block.entity;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cn.mcmod.briquettes.block.BriquettesCompressorBlock;
import cn.mcmod.briquettes.block.entity.handler.BriquetteMachineItemHandler;
import cn.mcmod.briquettes.recipes.CompressorRecipe;
import cn.mcmod.briquettes.recipes.RecipeTypeRegistry;
import cn.mcmod_mmf.mmlib.block.entity.SyncedBlockEntity;
import cn.mcmod_mmf.mmlib.utils.LevelUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CompressorBlockEntity extends SyncedBlockEntity {

    private final ItemStackHandler inventory;
    private LazyOptional<IItemHandler> inputHandler;
    private LazyOptional<IItemHandler> outputHandler;

    protected final ContainerData tileData;
    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

    private int recipeTime;
    private int recipeTimeTotal;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    public CompressorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.BRIQUETTE_COMPRESSOR.get(), pos, state);

        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new BriquetteMachineItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new BriquetteMachineItemHandler(inventory, this.getBlockState().getValue(BriquettesCompressorBlock.FACING)));
        this.tileData = createIntArray();
        this.experienceTracker = new Object2IntOpenHashMap<>();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, CompressorBlockEntity blockEntity) {
        boolean didInventoryChange = false;
        
        if (blockEntity.hasInput() && state.getValue(BriquettesCompressorBlock.LIT)) {
            Optional<CompressorRecipe> recipe = blockEntity
                    .getMatchingRecipe(new RecipeWrapper(blockEntity.inventory));
            if (recipe.isPresent() && blockEntity.canWork(recipe.get())) {
                didInventoryChange = blockEntity.processRecipe(recipe.get());
            } else {
                blockEntity.recipeTime = 0;
            }
        } else if (blockEntity.recipeTime > 0) {
            blockEntity.recipeTime = 0;
        }

        if (didInventoryChange) {
            blockEntity.inventoryChanged();
        }
    }

    private boolean hasInput() {
        if (!inventory.getStackInSlot(0).isEmpty()) {
            return true;
        }
        return false;
    }

    private Optional<CompressorRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (level == null) {
            return Optional.empty();
        }

        if (lastRecipeID != null) {
            Recipe<RecipeWrapper> recipe = level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.COMPRESSOR_RECIPE_TYPE.get()).stream()
                    .filter(now -> now.getId().equals(lastRecipeID)).findFirst().get();
            if (recipe instanceof CompressorRecipe) {
                if (recipe.matches(inventoryWrapper, level)) {
                    return Optional.of((CompressorRecipe) recipe);
                }
            }
        }

        if (checkNewRecipe) {
            Optional<CompressorRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeTypeRegistry.COMPRESSOR_RECIPE_TYPE.get(),
                    inventoryWrapper, level);
            if (recipe.isPresent()) {
                lastRecipeID = recipe.get().getId();
                return recipe;
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    protected boolean canWork(CompressorRecipe recipe) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem();
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outputStack = inventory.getStackInSlot(1);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!outputStack.sameItem(resultStack)) {
                    return false;
                } else if (outputStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(1)) {
                    return true;
                } else {
                    return outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean processRecipe(CompressorRecipe recipe) {
        if (level == null) {
            return false;
        }

        ++recipeTime;
        recipeTimeTotal = recipe.getRecipeTime();
        if (recipeTime < recipeTimeTotal) {
            return false;
        }

        recipeTime = 0;

        ItemStack resultStack = recipe.getResultItem();
        ItemStack outStack = inventory.getStackInSlot(1);

        if (outStack.isEmpty()) {
            inventory.setStackInSlot(1, resultStack.copy());
        } else if (outStack.sameItem(resultStack)) {
            outStack.grow(resultStack.getCount());
        }

        trackRecipeExperience(recipe);
        this.level.playSound(null, worldPosition, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1F, 1F);
        ItemStack slotStack = inventory.getStackInSlot(0);
        if (slotStack.hasContainerItem()) {
            double x = worldPosition.getX() + 0.5;
            double y = worldPosition.getY() + 0.7;
            double z = worldPosition.getZ() + 0.5;
            LevelUtils.spawnItemEntity(level, inventory.getStackInSlot(0).getContainerItem(), x, y, z, 0F, 0.25F,
                    0F);
        }
        if (!slotStack.isEmpty()) {
            slotStack.shrink(1);
        }
        
        return true;
    }

    public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            experienceTracker.addTo(recipeID, 1);
        }
    }

    public void clearUsedRecipes(Player player) {
        grantStoredRecipeExperience(player.level, player.position());
        experienceTracker.clear();
    }

    public void grantStoredRecipeExperience(Level world, Vec3 pos) {
        for (Object2IntMap.Entry<ResourceLocation> entry : experienceTracker.object2IntEntrySet()) {
            world.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe -> LevelUtils.splitAndSpawnExperience(world,
                    pos, entry.getIntValue(), ((CompressorRecipe) recipe).getExperience()));
        }
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            if (side == null || side.equals(Direction.UP)) {
                return inputHandler.cast();
            } else {
                return outputHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 2; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }
    
    public boolean addItem(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            boolean flag = true;
            ItemStack inputStack = inventory.getStackInSlot(0);
            if (inputStack.isEmpty()) {
                flag = true;
            } else if (!inputStack.sameItem(itemStack)) {
                flag = false;
            } else if (inputStack.getCount() + itemStack.getCount() <= inventory.getSlotLimit(0)) {
                flag = true;
            } else {
                flag = inputStack.getCount() + itemStack.getCount() <= itemStack.getMaxStackSize();
            }
            if(flag) {
                if (inputStack.isEmpty()) {
                    inventory.setStackInSlot(0, itemStack.copy());
                } else if (inputStack.sameItem(itemStack)) {
                    inputStack.grow(itemStack.getCount());
                }
                itemStack.shrink(itemStack.getCount());
                inventoryChanged();
                return true;
            }
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!inventory.getStackInSlot(0).isEmpty()) {
            ItemStack item = inventory.getStackInSlot(0);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }
    
    public ItemStack removeResultItem() {
        if (!inventory.getStackInSlot(1).isEmpty()) {
            ItemStack item = inventory.getStackInSlot(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
        recipeTimeTotal = compound.getInt("RecipeTimeTotal");
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("RecipeTime", recipeTime);
        compound.putInt("RecipeTimeTotal", recipeTimeTotal);
        compound.put("Inventory", inventory.serializeNBT());
        CompoundTag compoundRecipes = new CompoundTag();
        experienceTracker
                .forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < 1) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                case 0:
                    return CompressorBlockEntity.this.recipeTime;
                case 1:
                    return CompressorBlockEntity.this.recipeTimeTotal;
                default:
                    return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                case 0:
                    CompressorBlockEntity.this.recipeTime = value;
                    break;
                case 1:
                    CompressorBlockEntity.this.recipeTimeTotal = value;
                    break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.inputHandler = LazyOptional.of(() -> new BriquetteMachineItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new BriquetteMachineItemHandler(inventory, this.getBlockState().getValue(BriquettesCompressorBlock.FACING)));
    }
}
