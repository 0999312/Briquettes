package cn.mcmod.briquettes.data;

import org.jetbrains.annotations.Nullable;

import cn.mcmod.briquettes.item.ItemRegistry;
import cn.mcmod.briquettes.tags.BriquettesTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BriquettesItemTagsProvider extends ItemTagsProvider {

    public BriquettesItemTagsProvider(DataGenerator datagen, BlockTagsProvider blocktags, String modId,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(datagen, blocktags, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BriquettesTags.BAMBOO).add(Items.BAMBOO);
        tag(BriquettesTags.DUSTS_WOOD).addTag(BriquettesTags.SAWDUST);
        tag(BriquettesTags.SAWDUST).add(ItemRegistry.SAWDUST.get());
        tag(BriquettesTags.SAWDUST).add(ItemRegistry.STRAW_SCRAPS.get());
        
        tag(BriquettesTags.DUSTS_COAL).add(ItemRegistry.COAL_POWDER.get());
        tag(BriquettesTags.DUSTS_CHARCOAL).add(ItemRegistry.CHARCOAL_POWDER.get());
        
        tag(ItemTags.COALS).add(ItemRegistry.COAL_BRIQUETTE.get());
        tag(ItemTags.COALS).add(ItemRegistry.CHARCOAL_BRIQUETTE.get());
        tag(BriquettesTags.CHARCOAL).add(ItemRegistry.CHARCOAL_BRIQUETTE.get());
        
    }
    
}
