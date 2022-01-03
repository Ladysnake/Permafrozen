package net.ladysnake.permafrozen.util;

import net.minecraft.util.math.BlockPos;

public class MutableVec3d {
	private double x;
	private double z;
	private double y;

	public MutableVec3d(BlockPos start) {
		this.x = start.getX();
		this.y = start.getY();
		this.z = start.getZ();
	}

	public void move(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public double getY() {
		return this.y;
	}

	public void transferTo(BlockPos.Mutable mutable) {
		mutable.set((int) this.x, (int) this.y, (int) this.z);
	}
}
