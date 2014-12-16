package com.github.nickardson.augmentium.script.api;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class AABB extends AxisAlignedBB {
    public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
    }

    public AABB(BlockPos pos1, BlockPos pos2) {
        super(pos1, pos2);
    }

    public AABB(AxisAlignedBB orig) {
        super(orig.minX, orig.minY, orig.minZ, orig.maxX, orig.maxY, orig.maxZ);
    }

    public AABB include(double x, double y, double z) {
        return new AABB(super.addCoord(x, y, z));
    }

    public boolean contains(double x, double y, double z) {
        return super.isVecInside(new Vec3(x, y, z));
    }

    public boolean intersects(AxisAlignedBB other) {
        return super.intersectsWith(other);
    }
}
