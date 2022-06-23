package cn.mcmod.briquettes.recipes;

import cn.mcmod.briquettes.Briquettes;
import cn.mcmod_mmf.mmlib.recipe.AbstractRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeRegistry {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister
            .create(Registry.RECIPE_TYPE_REGISTRY, Briquettes.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(ForgeRegistries.RECIPE_SERIALIZERS, Briquettes.MODID);
    
    public static final RegistryObject<RecipeType<WoodChipperRecipe>> WOOD_CHIPPER_RECIPE_TYPE = RECIPE_TYPES
            .register("wood_chipper", () -> recipeType("wood_chipper"));

    public static final RegistryObject<AbstractRecipeSerializer<WoodChipperRecipe>> WOOD_CHIPPER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("wood_chipper", () -> new AbstractRecipeSerializer<WoodChipperRecipe>(WoodChipperRecipe.class));
    
    public static final RegistryObject<RecipeType<CompressorRecipe>> COMPRESSOR_RECIPE_TYPE = RECIPE_TYPES
            .register("compressor", () -> recipeType("compressor"));

    public static final RegistryObject<AbstractRecipeSerializer<CompressorRecipe>> COMPRESSOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS
            .register("compressor", () -> new AbstractRecipeSerializer<CompressorRecipe>(CompressorRecipe.class));
    
    private static <T extends Recipe<?>> RecipeType<T> recipeType(String name) {
        return new RecipeType<T>() {
            public String toString() {
                return new ResourceLocation(Briquettes.MODID, name).toString();
            }
        };
    }
}
