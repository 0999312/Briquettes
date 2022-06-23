package cn.mcmod.briquettes.compat.jei;

import cn.mcmod.briquettes.Briquettes;
import cn.mcmod.briquettes.block.BlockRegistry;
import cn.mcmod.briquettes.recipes.CompressorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class CompressorCategory implements IRecipeCategory<CompressorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Briquettes.MODID, "compressor");
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CompressorCategory(IGuiHelper helper) {
        title = new TranslatableComponent("briquettes.jei.compressor");
        ResourceLocation backgroundImage = new ResourceLocation(Briquettes.MODID, "textures/gui/jei_compat.png");
        background = helper.createDrawable(backgroundImage, 0, 0, 93, 46);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.BRIQUETTE_COMPRESSOR.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends CompressorRecipe> getRecipeClass() {
        return CompressorRecipe.class;
    }
    
    @Override
    public RecipeType<CompressorRecipe> getRecipeType() {
        return JEIPlugin.COMPRESSOR_JEI_TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompressorRecipe recipe, IFocusGroup focuses) {
      NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
      builder.addSlot(RecipeIngredientRole.INPUT, 14, 15).addIngredients(recipeIngredients.get(0));  
      builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 15).addItemStack(recipe.getResultItem());
    }
}
