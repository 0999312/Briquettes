package cn.mcmod.briquettes.recipes.builder;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import cn.mcmod.briquettes.recipes.RecipeTypeRegistry;
import cn.mcmod.briquettes.recipes.WoodChipperRecipe;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class WoodChipperRecipeBuilder {
    private final ItemStack result;
    private Ingredient ingredients;
    private final float experience;
    private final int recipeTime;

    private WoodChipperRecipeBuilder(ItemLike resultItem, int count, float exp, int time) {
        this.result = new ItemStack(resultItem.asItem(), count);
        this.experience = exp;
        this.recipeTime = time;
    }

    public static WoodChipperRecipeBuilder chipper(ItemLike resultItem) {
        return new WoodChipperRecipeBuilder(resultItem, 1, 0F, 1200);
    }

    public static WoodChipperRecipeBuilder chipper(ItemLike resultItem, int count) {
        return new WoodChipperRecipeBuilder(resultItem, count, 0F, 1200);
    }

    public static WoodChipperRecipeBuilder chipper(ItemLike resultItem, float exp, int time) {
        return new WoodChipperRecipeBuilder(resultItem, 1, exp, time);
    }

    public static WoodChipperRecipeBuilder chipper(ItemLike resultItem, int count, float exp, int time) {
        return new WoodChipperRecipeBuilder(resultItem, count, exp, time);
    }

    public WoodChipperRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public WoodChipperRecipeBuilder requires(ItemLike item) {
        this.requires(Ingredient.of(item));
        return this;
    }

    public WoodChipperRecipeBuilder requires(Ingredient ingre) {
        this.ingredients=ingre;
        return this;
    }


    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new WoodChipperRecipeBuilder.Result(id, this.result, this.ingredients, this.experience,
                this.recipeTime));
    }

    public static class Result implements FinishedRecipe {

        private final WoodChipperRecipe recipe = new WoodChipperRecipe();

        public Result(ResourceLocation id, ItemStack result, Ingredient ingredient, float exp, int time) {
            recipe.setId(id);
            recipe.output = result;
            recipe.inputItems = ingredient;
            recipe.experience = exp;
            recipe.recipeTime = time;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject recipeJson = RecipeTypeRegistry.WOOD_CHIPPER_RECIPE_SERIALIZER.get().toJson(recipe);
            json.add("ingredient", recipeJson.get("ingredient"));
            json.add("result", recipeJson.get("result"));
            json.add("experience", recipeJson.get("experience"));
            json.add("recipeTime", recipeJson.get("recipeTime"));
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeTypeRegistry.WOOD_CHIPPER_RECIPE_SERIALIZER.get();
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
