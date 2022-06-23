package cn.mcmod.briquettes.compat.jei;

import java.util.List;

import cn.mcmod.briquettes.Briquettes;
import cn.mcmod.briquettes.block.BlockRegistry;
import cn.mcmod.briquettes.recipes.CompressorRecipe;
import cn.mcmod.briquettes.recipes.RecipeTypeRegistry;
import cn.mcmod.briquettes.recipes.WoodChipperRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = new ResourceLocation(Briquettes.MODID, "jei_plugin");

    private static final Minecraft MC = Minecraft.getInstance();

    private static <C extends Container, T extends Recipe<C>> List<T> findRecipesByType(RecipeType<T> type) {
        return MC.level.getRecipeManager().getAllRecipesFor(type);
    }
    
    public static final mezz.jei.api.recipe.RecipeType<WoodChipperRecipe> WOOD_CHIPPER_JEI_TYPE = 
            mezz.jei.api.recipe.RecipeType.create(Briquettes.MODID, "wood_chipper", WoodChipperRecipe.class);
    
    public static final mezz.jei.api.recipe.RecipeType<CompressorRecipe> COMPRESSOR_JEI_TYPE = 
            mezz.jei.api.recipe.RecipeType.create(Briquettes.MODID, "compressor", CompressorRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new WoodChipperCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CompressorCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(WOOD_CHIPPER_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.WOOD_CHIPPER_RECIPE_TYPE.get()));
        registration.addRecipes(COMPRESSOR_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.COMPRESSOR_RECIPE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.WOOD_CHIPPER.get()), WOOD_CHIPPER_JEI_TYPE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.BRIQUETTE_COMPRESSOR.get()), COMPRESSOR_JEI_TYPE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

}
