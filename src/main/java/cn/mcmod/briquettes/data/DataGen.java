package cn.mcmod.briquettes.data;

import cn.mcmod.briquettes.Briquettes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        dataGenerator.addProvider(new BriquettesBlockStateProvider(dataGenerator, Briquettes.MODID, existingFileHelper));
        dataGenerator.addProvider(new BriquettesItemModelProvider(dataGenerator, Briquettes.MODID, existingFileHelper));
        BriquettesBlockTagsProvider block_tag = new BriquettesBlockTagsProvider(dataGenerator, Briquettes.MODID, existingFileHelper);
        dataGenerator.addProvider(block_tag);
        dataGenerator.addProvider(new BriquettesItemTagsProvider(dataGenerator, block_tag, Briquettes.MODID, existingFileHelper));
        dataGenerator.addProvider(new BriquettesRecipeProvider(dataGenerator));

    }
}

 