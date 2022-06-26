package cn.mcmod.briquettes.data;

import cn.mcmod.briquettes.block.BlockRegistry;
import cn.mcmod_mmf.mmlib.data.AbstractBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BriquettesBlockStateProvider extends AbstractBlockStateProvider {

    public BriquettesBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        log(BlockRegistry.SAWDUST_BRIQUETTES_PIT);
        log(BlockRegistry.COAL_BRIQUETTES_PIT);
        log(BlockRegistry.CHARCOAL_BRIQUETTES_PIT);
    }

}
