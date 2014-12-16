package com.github.nickardson.augmentium.script.api;

import net.minecraft.tileentity.TileEntity;

public class ProxyTileEntity {
    private TileEntity entity;
    public ProxyTileEntity(TileEntity entity) {
        this.entity = entity;
    }

    public String getName() {
        return entity.getBlockType().getUnlocalizedName();
    }

    public int getX() {
        return entity.getPos().getX();
    }

    public int getY() {
        return entity.getPos().getY();
    }

    public int getZ() {
        return entity.getPos().getZ();
    }
}
