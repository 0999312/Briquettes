package cn.mcmod.briquettes.data;

import cn.mcmod.briquettes.item.ItemRegistry;
import cn.mcmod_mmf.mmlib.data.AbstractItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BriquettesItemModelProvider extends AbstractItemModelProvider {

    public BriquettesItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
      ItemRegistry.ITEMS.getEntries().forEach((item)->{
          if(item.get() instanceof BlockItem block)
              itemBlock(block::getBlock);
          else normalItem(item);
      });
    }

}
