package com.github.nickardson.augmentium;

import com.github.nickardson.gui.util.RenderUtility;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;

public class AugmentiumListener {
    @SubscribeEvent(receiveCanceled = true)
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (event.isCancelable() || (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR))
            return;

        RenderUtility.scale1to1();
        RenderUtility.startOverlay();
        AugmentiumMod.client.getOnRender().trigger(event.partialTicks, event.mouseX, event.mouseY);
        RenderUtility.endOverlay();
        RenderUtility.unscale();
    }

    @SubscribeEvent()
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side.isClient()) {
            AugmentiumMod.world.getOnTick().trigger();
        }
    }

    @SubscribeEvent()
    public void onWorldLoad(WorldEvent.Load event) {
        AugmentiumMod.world.getOnLoad().trigger(event.world.provider.dimensionId);
    }
}
