package cn.mcmod.briquettes.block.entity;

import cn.mcmod.briquettes.Briquettes;
import cn.mcmod.briquettes.block.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, Briquettes.MODID);
    
    public static final RegistryObject<BlockEntityType<WoodChipperBlockEntity>> WOOD_CHIPPER = BLOCK_ENTITIES
            .register("wood_chipper", () -> BlockEntityType.Builder
                    .of(WoodChipperBlockEntity::new, BlockRegistry.WOOD_CHIPPER.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<CompressorBlockEntity>> BRIQUETTE_COMPRESSOR = BLOCK_ENTITIES
            .register("compressor", () -> BlockEntityType.Builder
                    .of(CompressorBlockEntity::new, BlockRegistry.BRIQUETTE_COMPRESSOR.get()).build(null));
}
