package cn.mcmod.briquettes.data;

import java.util.function.Consumer;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import blusunrize.immersiveengineering.api.crafting.builders.BlastFurnaceFuelBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.CokeOvenRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.CrusherRecipeBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.MetalPressRecipeBuilder;
import blusunrize.immersiveengineering.common.register.IEItems.Molds;
import cn.mcmod.briquettes.Briquettes;
import cn.mcmod.briquettes.item.ItemRegistry;
import cn.mcmod.briquettes.recipes.builder.BriquetteCompressorRecipeBuilder;
import cn.mcmod.briquettes.recipes.builder.WoodChipperRecipeBuilder;
import cn.mcmod.briquettes.tags.BriquettesTags;
import cn.mcmod_mmf.mmlib.data.AbstractRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fluids.FluidAttributes;

public class BriquettesRecipeProvider extends AbstractRecipeProvider {

    public BriquettesRecipeProvider(DataGenerator gen) {
        super(gen);
    }
    
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> comsumer) {
        ShapedRecipeBuilder.shaped(ItemRegistry.WOOD_CHIPPER.get())
        .pattern(" D ")
        .pattern("ISP")
        .pattern("SSS")
        .define('D', Items.HOPPER)
        .define('I', Tags.Items.INGOTS_IRON)
        .define('P', Items.PISTON)
        .define('S', Items.POLISHED_DIORITE)
        .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE))
        .save(comsumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.BRIQUETTE_COMPRESSOR.get())
        .pattern(" D ")
        .pattern("ISP")
        .pattern("SSS")
        .define('D', Items.HOPPER)
        .define('I', Tags.Items.INGOTS_IRON)
        .define('P', Items.PISTON)
        .define('S', Items.POLISHED_ANDESITE)
        .unlockedBy("has_redstone",has(Tags.Items.DUSTS_REDSTONE))
        .save(comsumer);
        
        smeltingRecipe(ItemRegistry.CHARCOAL_BRIQUETTE.get(), ItemRegistry.SAWDUST_BRIQUETTE.get(), 0.5F);
        
        WoodChipperRecipeBuilder.chipper(ItemRegistry.SAWDUST.get())
        .requires(BriquettesTags.BAMBOO)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "sawdust_from_bamboo"));
        WoodChipperRecipeBuilder.chipper(ItemRegistry.SAWDUST.get(), 4)
        .requires(ItemTags.LOGS)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "sawdust_from_log"));
        WoodChipperRecipeBuilder.chipper(ItemRegistry.SAWDUST.get())
        .requires(ItemTags.PLANKS)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "sawdust_from_plank"));
        WoodChipperRecipeBuilder.chipper(ItemRegistry.SAWDUST.get())
        .requires(ItemTags.SAPLINGS)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "sawdust_from_sapings"));
        WoodChipperRecipeBuilder.chipper(ItemRegistry.STRAW_SCRAPS.get(), 3)
        .requires(Items.HAY_BLOCK)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "straw_scraps_from_hay"));
        
        WoodChipperRecipeBuilder.chipper(ItemRegistry.CHARCOAL_POWDER.get())
        .requires(BriquettesTags.CHARCOAL)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "charcoal_powder"));
        WoodChipperRecipeBuilder.chipper(ItemRegistry.COAL_POWDER.get())
        .requires(Items.COAL)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "coal_powder_from_coal"));
        WoodChipperRecipeBuilder.chipper(ItemRegistry.COAL_POWDER.get(),9)
        .requires(Items.COAL_BLOCK)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "coal_powder_from_coal_block"));
        
        BriquetteCompressorRecipeBuilder.compressor(ItemRegistry.SAWDUST_BRIQUETTE.get())
        .requires(BriquettesTags.DUSTS_WOOD)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "sawdust_briquette"));
        BriquetteCompressorRecipeBuilder.compressor(ItemRegistry.CHARCOAL_BRIQUETTE.get())
        .requires(BriquettesTags.DUSTS_CHARCOAL)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "charcoal_briquette"));
        BriquetteCompressorRecipeBuilder.compressor(ItemRegistry.COAL_BRIQUETTE.get())
        .requires(BriquettesTags.DUSTS_COAL)
        .save(comsumer, new ResourceLocation(Briquettes.MODID, "coal_briquette"));
        
        registerIERecipes(comsumer);
    }
    
    private void registerIERecipes(Consumer<FinishedRecipe> comsumer) {
        BlastFurnaceFuelBuilder.builder(ItemRegistry.COAL_BRIQUETTE.get())
        .addCondition(new ModLoadedCondition(ImmersiveEngineering.MODID))
        .setTime(360)
        .build(comsumer, new ResourceLocation(Briquettes.MODID, "compat/ie/blast_fuel/coal_briquette"));

        CokeOvenRecipeBuilder.builder(ItemRegistry.CHARCOAL_BRIQUETTE.get())
        .addCondition(new ModLoadedCondition(ImmersiveEngineering.MODID))
        .addInput(ItemRegistry.SAWDUST_BRIQUETTE.get())
        .setOil(FluidAttributes.BUCKET_VOLUME/4)
        .setTime(800)
        .build(comsumer,new ResourceLocation(Briquettes.MODID, "compat/ie/coke_oven/charcoal_briquette"));
        
        CrusherRecipeBuilder.builder(new ItemStack(ItemRegistry.SAWDUST.get(), 1))
        .addInput(BriquettesTags.BAMBOO)
        .setEnergy(2400)
        .build(comsumer,new ResourceLocation(Briquettes.MODID, "compat/ie/crusher/sawdust_from_bamboo"));
        
        CrusherRecipeBuilder.builder(new ItemStack(ItemRegistry.SAWDUST.get(), 1))
        .addSecondary(ItemRegistry.SAWDUST.get(), 0.5f)
        .addInput(ItemTags.PLANKS)
        .setEnergy(2400)
        .build(comsumer, new ResourceLocation(Briquettes.MODID, "compat/ie/crusher/sawdust_from_plank"));
        
        CrusherRecipeBuilder.builder(new ItemStack(ItemRegistry.STRAW_SCRAPS.get(), 6))
        .addSecondary(new ItemStack(ItemRegistry.STRAW_SCRAPS.get(), 3), 0.5f)
        .addInput(Items.HAY_BLOCK)
        .setEnergy(4800)
        .build(comsumer, new ResourceLocation(Briquettes.MODID, "compat/ie/crusher/straw_scraps_from_hay"));
        
        CrusherRecipeBuilder.builder(new ItemStack(Items.SUGAR, 4))
        .addSecondary(new ItemStack(ItemRegistry.STRAW_SCRAPS.get()), 0.25f)
        .addInput(Items.SUGAR_CANE)
        .setEnergy(2400)
        .build(comsumer, new ResourceLocation(Briquettes.MODID, "compat/ie/crusher/straw_scraps_from_sugar_cane"));

        CrusherRecipeBuilder.builder(BriquettesTags.DUSTS_COAL, 9)
        .addSecondary(BriquettesTags.DUSTS_COAL, 0.75f)
        .addInput(Items.COAL_BLOCK)
        .setEnergy(4800)
        .build(comsumer, new ResourceLocation(Briquettes.MODID, "compat/ie/crusher/coal_powder_from_coal_block"));
        
        MetalPressRecipeBuilder.builder(Molds.MOLD_ROD, new ItemStack(ItemRegistry.SAWDUST_BRIQUETTE.get(), 3))
        .addInput(new IngredientWithSize(BriquettesTags.DUSTS_WOOD, 2))
        .setEnergy(2400)
        .build(comsumer,new ResourceLocation(Briquettes.MODID, "compat/ie/metal_press/sawdust_briquette"));
        
        MetalPressRecipeBuilder.builder(Molds.MOLD_ROD, new ItemStack(ItemRegistry.CHARCOAL_BRIQUETTE.get(), 3))
        .addInput(new IngredientWithSize(BriquettesTags.DUSTS_CHARCOAL, 2))
        .setEnergy(2400)
        .build(comsumer,new ResourceLocation(Briquettes.MODID, "compat/ie/metal_press/charcoal_briquette"));
        
        MetalPressRecipeBuilder.builder(Molds.MOLD_ROD, new ItemStack(ItemRegistry.COAL_BRIQUETTE.get(), 3))
        .addInput(new IngredientWithSize(BriquettesTags.DUSTS_COAL, 2))
        .setEnergy(2400)
        .build(comsumer,new ResourceLocation(Briquettes.MODID, "compat/ie/metal_press/coal_briquette"));
    }

}
