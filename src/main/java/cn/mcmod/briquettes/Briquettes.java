package cn.mcmod.briquettes;

import com.mojang.logging.LogUtils;

import cn.mcmod.briquettes.block.BlockRegistry;
import cn.mcmod.briquettes.block.entity.BlockEntityTypeRegistry;
import cn.mcmod.briquettes.item.ItemRegistry;
import cn.mcmod.briquettes.recipes.RecipeTypeRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

@Mod(Briquettes.MODID)
public class Briquettes {
    public static final String MODID = "briquettes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.CHARCOAL_BRIQUETTE.get());
        }
    };

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties().tab(Briquettes.GROUP);
    }
    
    public Briquettes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityTypeRegistry.BLOCK_ENTITIES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        RecipeTypeRegistry.RECIPE_TYPES.register(modEventBus);
        RecipeTypeRegistry.RECIPE_SERIALIZERS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
