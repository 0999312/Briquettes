package cn.mcmod.briquettes.block;

import cn.mcmod.briquettes.Briquettes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Briquettes.MODID);
    
    public static final RegistryObject<Block> WOOD_CHIPPER = BLOCKS.register("wood_chipper", WoodChipperBlock::new);
    public static final RegistryObject<Block> BRIQUETTE_COMPRESSOR = BLOCKS.register("compressor", BriquettesCompressorBlock::new);
    
    public static final RegistryObject<RotatedPillarBlock> SAWDUST_BRIQUETTES_PIT = BLOCKS.register("sawdust_briquettes_pit",
            () -> pit(MaterialColor.WOOD, SoundType.WOOD));
    public static final RegistryObject<RotatedPillarBlock> CHARCOAL_BRIQUETTES_PIT = BLOCKS.register("charcoal_briquettes_pit",
            () -> pit(MaterialColor.COLOR_GRAY, SoundType.WOOD));
    public static final RegistryObject<RotatedPillarBlock> COAL_BRIQUETTES_PIT = BLOCKS.register("coal_briquettes_pit",
            () -> pit(MaterialColor.COLOR_BLACK, SoundType.STONE));
    
    private static RotatedPillarBlock pit(MaterialColor top, SoundType type) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .of(Material.WOOD, top).strength(2.0F).sound(type));
    }
}
