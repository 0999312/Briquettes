package cn.mcmod.briquettes.block;

import cn.mcmod.briquettes.Briquettes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Briquettes.MODID);
    
    public static final RegistryObject<Block> WOOD_CHIPPER = BLOCKS.register("wood_chipper", WoodChipperBlock::new);
    public static final RegistryObject<Block> BRIQUETTE_COMPRESSOR = BLOCKS.register("compressor", BriquettesCompressorBlock::new);
}
