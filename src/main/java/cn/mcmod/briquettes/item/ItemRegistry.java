package cn.mcmod.briquettes.item;

import java.util.function.Supplier;

import cn.mcmod.briquettes.Briquettes;
import cn.mcmod.briquettes.block.BlockRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Briquettes.MODID);
    
    public static final RegistryObject<BlockItem> WOOD_CHIPPER = ITEMS.register("wood_chipper",
            () -> new BlockItem(BlockRegistry.WOOD_CHIPPER.get(), Briquettes.defaultItemProperties()));
    public static final RegistryObject<BlockItem> BRIQUETTE_COMPRESSOR = ITEMS.register("compressor",
            () -> new BlockItem(BlockRegistry.BRIQUETTE_COMPRESSOR.get(), Briquettes.defaultItemProperties()));
    
    public static final RegistryObject<BlockItem> SAWDUST_BRIQUETTES_PIT = ITEMS.register("sawdust_briquettes_pit",
            () -> new BlockItem(BlockRegistry.SAWDUST_BRIQUETTES_PIT.get(), Briquettes.defaultItemProperties()));
    public static final RegistryObject<BlockItem> CHARCOAL_BRIQUETTES_PIT = ITEMS.register("charcoal_briquettes_pit",
            () -> new BlockItem(BlockRegistry.CHARCOAL_BRIQUETTES_PIT.get(), Briquettes.defaultItemProperties()));
    public static final RegistryObject<BlockItem> COAL_BRIQUETTES_PIT = ITEMS.register("coal_briquettes_pit",
            () -> new BlockItem(BlockRegistry.COAL_BRIQUETTES_PIT.get(), Briquettes.defaultItemProperties()));
    
    public static final RegistryObject<Item> SAWDUST = register("sawdust", ItemRegistry::normalItem);
    public static final RegistryObject<Item> STRAW_SCRAPS = register("straw_scraps", ItemRegistry::normalItem);
    public static final RegistryObject<Item> CHARCOAL_POWDER = register("charcoal_powder", ItemRegistry::normalItem);
    public static final RegistryObject<Item> COAL_POWDER = register("coal_powder", ItemRegistry::normalItem);
    public static final RegistryObject<Item> SAWDUST_BRIQUETTE = register("sawdust_briquette", ItemRegistry::normalItem);
    public static final RegistryObject<Item> CHARCOAL_BRIQUETTE = register("charcoal_briquette", ItemRegistry::normalItem);
    public static final RegistryObject<Item> COAL_BRIQUETTE = register("coal_briquette", ItemRegistry::normalItem);
    
    private static Item normalItem() {
        return new Item(Briquettes.defaultItemProperties());
    }

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }
}
