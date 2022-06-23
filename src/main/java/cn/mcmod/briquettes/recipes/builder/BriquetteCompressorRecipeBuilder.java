package cn.mcmod.briquettes.recipes.builder;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import cn.mcmod.briquettes.recipes.CompressorRecipe;
import cn.mcmod.briquettes.recipes.RecipeTypeRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class BriquetteCompressorRecipeBuilder {
    private final ItemStack result;
    private Ingredient ingredients;
    private final float experience;
    private final int recipeTime;

    private BriquetteCompressorRecipeBuilder(ItemLike resultItem, int count, float exp, int time) {
        this.result = new ItemStack(resultItem.asItem(), count);
        this.experience = exp;
        this.recipeTime = time;
    }

    public static BriquetteCompressorRecipeBuilder compressor(ItemLike resultItem) {
        return new BriquetteCompressorRecipeBuilder(resultItem, 1, 0F, 1);
    }

    public static BriquetteCompressorRecipeBuilder compressor(ItemLike resultItem, int count) {
        return new BriquetteCompressorRecipeBuilder(resultItem, count, 0F, 1);
    }

    public static BriquetteCompressorRecipeBuilder compressor(ItemLike resultItem, float exp, int time) {
        return new BriquetteCompressorRecipeBuilder(resultItem, 1, exp, time);
    }

    public static BriquetteCompressorRecipeBuilder compressor(ItemLike resultItem, int count, float exp, int time) {
        return new BriquetteCompressorRecipeBuilder(resultItem, count, exp, time);
    }

    public BriquetteCompressorRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public BriquetteCompressorRecipeBuilder requires(ItemLike item) {
        this.requires(Ingredient.of(item));
        return this;
    }

    public BriquetteCompressorRecipeBuilder requires(Ingredient ingre) {
        this.ingredients=ingre;
        return this;
    }


    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new BriquetteCompressorRecipeBuilder.Result(id, this.result, this.ingredients, this.experience,
                this.recipeTime));
    }

    public static class Result implements FinishedRecipe {

        private final CompressorRecipe recipe = new CompressorRecipe();

        public Result(ResourceLocation id, ItemStack result, Ingredient ingredient, float exp, int time) {
            recipe.setId(id);
            recipe.output = result;
            recipe.inputItems = ingredient;
            recipe.experience = exp;
            recipe.recipeTime = time;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject recipeJson = RecipeTypeRegistry.COMPRESSOR_RECIPE_SERIALIZER.get().toJson(recipe);
            json.add("ingredient", recipeJson.get("ingredient"));
            json.add("result", recipeJson.get("result"));
            json.add("experience", recipeJson.get("experience"));
            json.add("recipeTime", recipeJson.get("recipeTime"));
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeTypeRegistry.COMPRESSOR_RECIPE_SERIALIZER.get();
        }

        @Override
        public ResourceLocation getId() {
            return recipe.getId();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
