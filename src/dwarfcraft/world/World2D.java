package dwarfcraft.world;

import java.util.Random;

import utils.PerlinNoise;

public class World2D extends World<int[][]> {

	Random rand;
	private PerlinNoise p;
	private int r;
	private int size;

	private int shoreLine = 90;
	private int treeLine = 180;
	private int snowLine = 210;

	public enum BlockType {
		WATER, GRASS, STONE, SNOW;
	}

	public World2D(int worldSize) {

		this.rand = new Random();

		size = worldSize;
		p = new PerlinNoise();
		r = rand.nextInt();
		this.worldData = p.perlin2D(r, worldSize, 6, .7f, 2f,
				1 / ((float) worldSize), 256);
		convertToBlocks();
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
	public BlockType getBlock(int x, int y) {
		return BlockType.values()[this.worldData[x][y]];
	}

	@Override
	public void regen(float freq, float lac, float per, int octaves) {
		this.worldData = p.perlin2D(r, size, octaves, per, lac, freq, 256);
		convertToBlocks();
	}

	public void convertToBlocks() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				int height = this.worldData[x][y];
				if (height > snowLine) {
					this.worldData[x][y] = BlockType.SNOW.ordinal();
				} else if (height > treeLine) {
					this.worldData[x][y] = BlockType.STONE.ordinal();
				} else if (height > shoreLine) {
					this.worldData[x][y] = BlockType.GRASS.ordinal();
				} else {
					this.worldData[x][y] = BlockType.WATER.ordinal();
				}
			}
		}
	}
}
