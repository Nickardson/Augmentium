package com.github.nickardson.augmentium;

import com.github.nickardson.augmentium.script.api.APIClient;
import com.github.nickardson.augmentium.script.api.APIWorld;
import com.github.nickardson.gui.util.RenderUtility;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AugmentiumListener {
    @SubscribeEvent(receiveCanceled = true)
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (event.isCancelable() || (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR))
            return;

        RenderUtility.scale1to1();
        RenderUtility.startOverlay();
        APIClient.instance.getOnRender().trigger(event.partialTicks);
        RenderUtility.endOverlay();
        RenderUtility.unscale();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onRenderWorld(RenderWorldLastEvent event) {
        APIClient.instance.getOnRenderWorld().trigger(event.partialTicks);
    }

    @SubscribeEvent()
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side.isClient()) {
            APIWorld.instance.getOnTick().trigger();
        }
    }

    @SubscribeEvent()
    public void onWorldLoad(WorldEvent.Load event) {
        APIWorld.instance.getOnLoad().trigger(event.world.provider.getDimensionId());
    }
}
