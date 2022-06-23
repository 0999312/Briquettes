package cn.mcmod.briquettes.recipes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import cn.mcmod_mmf.mmlib.recipe.AbstractRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class WoodChipperRecipe extends AbstractRecipe {

    @Expose()
    @SerializedName("ingredient")
    public Ingredient inputItems;
    
    @Expose()
    @SerializedName("result")
    public ItemStack output;
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, inputItems);
    }
    
    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return inputItems.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.output;
    }

    @Override
    public boolean canCraftInDimensions(int u, int v) {
        return u*v==1;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.WOOD_CHIPPER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.WOOD_CHIPPER_RECIPE_TYPE.get();
    }

}
