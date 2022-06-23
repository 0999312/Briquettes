package cn.mcmod.briquettes;

import java.util.function.Supplier;

import cn.mcmod.briquettes.item.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class BriquettesEventHandler {
    @SubscribeEvent
    public static void registerBurnTime(FurnaceFuelBurnTimeEvent event) {
        register(event, ItemRegistry.COAL_BRIQUETTE, 2000);
        register(event, ItemRegistry.CHARCOAL_BRIQUETTE, 2000);
        register(event, ItemRegistry.SAWDUST_BRIQUETTE, 800);
    }

    private static void register(FurnaceFuelBurnTimeEvent event, Supplier<? extends Item> item, int burnTime) {
        register(event, item.get(), burnTime);
    }

    private static void register(FurnaceFuelBurnTimeEvent event, Item item, int burnTime) {
        register(event, new ItemStack(item), burnTime);
    }

    private static void register(FurnaceFuelBurnTimeEvent event, ItemStack item, int burnTime) {
        if (item.getItem() == event.getItemStack().getItem())
            event.setBurnTime(burnTime);
    }
}