package dwarfcraft.world;

public abstract class World<T> {
	T worldData;

	public abstract int getBlockWidth();
	public abstract int getBlockHeight();
	public abstract int getBlock(int x, int y);
	public abstract void regen(float freq, float lac, float per, int object);
}
