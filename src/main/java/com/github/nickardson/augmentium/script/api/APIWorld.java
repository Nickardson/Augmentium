package com.github.nickardson.augmentium.script.api;

import com.github.nickardson.augmentium.script.EventHook;
import net.minecraft.client.Minecraft;

public class APIWorld {
    public static APIWorld instance = new APIWorld();

    private final EventHook onTick = new EventHook();
    public EventHook getOnTick() {return onTick;}

    private final EventHook onLoad = new EventHook();
    public EventHook getOnLoad() {return onLoad;}

    public ProxyEntity[] getPlayers() {
        Object[] plrIn = Minecraft.getMinecraft().theWorld.playerEntities.toArray();
        ProxyEntity[] out = new ProxyEntity[plrIn.length];
        for (int i = 0; i < plrIn.length; i++) {
            out[i] = new ProxyEntity((net.minecraft.entity.player.EntityPlayer) plrIn[i]);
        }
        return out;
    }

    public ProxyEntity[] getEntities() {
        Object[] entIn = Minecraft.getMinecraft().theWorld.loadedEntityList.toArray();
        ProxyEntity[] out = new ProxyEntity[entIn.length];
        for (int i = 0; i < entIn.length; i++) {
            out[i] = new ProxyEntity((net.minecraft.entity.Entity) entIn[i]);
        }
        return out;
    }

    public ProxyTileEntity[] getTileEntities() {
        Object[] plrIn = Minecraft.getMinecraft().theWorld.loadedTileEntityList.toArray();
        ProxyTileEntity[] out = new ProxyTileEntity[plrIn.length];
        for (int i = 0; i < plrIn.length; i++) {
            out[i] = new ProxyTileEntity((net.minecraft.tileentity.TileEntity) plrIn[i]);
        }
        return out;
    }

    public ProxyEntity getUser() {
        return new ProxyEntity(Minecraft.getMinecraft().thePlayer);
    }

    @Override
    public String toString() {
        return "[api World]";
    }
}
