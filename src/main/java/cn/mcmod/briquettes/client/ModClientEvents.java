package cn.mcmod.briquettes.client;

import cn.mcmod.briquettes.Briquettes;
import cn.mcmod.briquettes.block.BlockRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Briquettes.MODID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void clientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.WOOD_CHIPPER.get(), RenderType.cutoutMipped());
        });
    }
}
