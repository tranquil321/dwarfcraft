package dwarfcraft.world;

import dwarfcraft.world.World2D.BlockType;

public abstract class World<T> {
	T worldData;

	public abstract int getBlockWidth();
	public abstract int getBlockHeight();
	public abstract BlockType getBlock(int x, int y);
	public abstract void regen(float freq, float lac, float per, int object);
}
