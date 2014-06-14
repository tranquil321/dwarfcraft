package dwarfcraft.world;

import java.util.Random;

import utils.PerlinNoise;

public class World2D extends World<int[][]> {

	Random rand;
	private PerlinNoise p;
	private int r;
	private int size;
	
	public World2D(int worldSize) {

		this.rand = new Random();
		
		size = worldSize;
		p = new PerlinNoise();
		r = rand.nextInt();
		this.worldData = p.perlin2D(r, worldSize, 6, .7f, 2f, 1/((float)worldSize), 256);
		
	}

	@Override
	public int getBlockWidth() {
		return this.worldData.length;
	}

	@Override
	public int getBlockHeight() {
		return this.worldData[0].length;
	}

	@Override
	public int getBlock(int x, int y) {
		return this.worldData[x][y];
	}

	@Override
	public void regen(float freq, float lac, float per, int octaves) {
		this.worldData = p.perlin2D(r, size, octaves, per, lac, freq, 256);
	}
	
}
