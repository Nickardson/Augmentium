package com.github.nickardson.augmentium.script.api;

import net.minecraft.entity.Entity;

public class ProxyEntity {
    private Entity entity;
    public ProxyEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Gets the Bounding Box of this entity
     * @return The entity's Axis Aligned Bounding Box
     */
    public AABB getBb() {
        return new AABB(entity.getEntityBoundingBox());
    }

    /**
     * Gets the Bounding Box of this entity, offset to match it's rendered position.
     * @param pt Partial ticks
     * @return The entity's Axis Aligned Bounding Box as seen when rendered
     */
    public AABB getBbRender(float pt) {
        return new AABB(entity.getEntityBoundingBox().offset(getX(pt) - getX(), getY(pt) - getY(), getZ(pt) - getZ()));
    }

    /**
     * @return The width of this entity.
     */
    public float getWidth() {
        return entity.width;
    }

    /**
     * @return The height of this entity
     */
    public float getHeight() {
        return entity.height;
    }

    /**
     * @return The entity ID of this entity
     */
    public int getId() {
        return entity.getEntityId();
    }

    public String getName() {
        return entity.getName();
    }

    /**
     * @return The X coordinate of this entity
     */
    public double getX() {
        return entity.posX;
    }

    /**
     * Gets the Z coordinate of this entity as rendered.
     * @param pt Partial ticks
     * @return The apparent Z coordinate of this entity
     */
    public double getX(float pt) {
        return entity.prevPosX + (entity.posX - entity.prevPosX) * pt;
    }

    /**
     * @return The Y coordinate of this entity
     */
    public double getY() {
        return entity.posY;
    }

    /**
     * Gets the Y coordinate of this entity as rendered.
     * @param pt Partial ticks
     * @return The apparent Y coordinate of this entity
     */
    public double getY(float pt) {
        return entity.prevPosY + (entity.posY - entity.prevPosY) * pt;
    }

    /**
     * @return The Z coordinate of this entity
     */
    public double getZ() {
        return entity.posZ;
    }

    /**
     * Gets the Z coordinate of this entity as rendered.
     * @param pt Partial ticks
     * @return The apparent Z coordinate of this entity
     */
    public double getZ(float pt) {
        return entity.prevPosZ + (entity.posZ - entity.prevPosZ) * pt;
    }
}
