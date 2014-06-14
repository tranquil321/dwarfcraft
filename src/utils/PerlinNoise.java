package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class PerlinNoise {
	private int SEED;
	private int SIZE;
	private int OCTAVES;
	private float PERSISTENCE;
	private float LACUNARITY;
	private float FREQUENCY;
	private int[] p;
	private float[] gx;
	private float[] gy;
	private Random r;
	
	public float[][] initializeValues(int seed, int size, int octaves, float persistence,
			float lacunarity, float frequency){
		SEED = seed;
		SIZE = size;
		OCTAVES = octaves;
		PERSISTENCE = persistence;
		LACUNARITY = lacunarity;
		FREQUENCY = frequency;
		float[][] map = new float[SIZE][SIZE];
		r = new Random(SEED);
		return map;
	}
	public void initializePermutations(){
		p = new int[SIZE];
		for (int i = 0; i < SIZE; i++){p[i] = i;}
		for (int i = 0; i < SIZE; i++){
			int j = Math.abs(r.nextInt() % SIZE);
			int swap = p[i];
			p[i] = p[j];
			p[j] = swap;
		}
	}
	public void initializeGradients(){
		gx = new float[SIZE];
		gy = new float[SIZE];
		for (int i = 0; i < SIZE; i++){
			float theta = r.nextFloat() * 2 * ((float) Math.PI);
			gx[i] = (float) Math.cos(theta);
			gy[i] = (float) Math.sin(theta);
		}
	}
	public float dotProduct(float x, float y, int xcorner, int ycorner){
		int index = p[(ycorner + p[xcorner % SIZE]) % SIZE];
		float gradx = gx[index];
		float grady = gy[index];
		float xvec = x - xcorner;
		float yvec = y - ycorner;
		float product = gradx * xvec + grady * yvec;
		return product;
	}
	public float perlinValue(float x, float y){
		int x0 = (int) Math.floor(x); int x1 = x0 + 1;
		int y0 = (int) Math.floor(y); int y1 = y0 + 1;
		float s = dotProduct(x, y, x0, y0);
		float t = dotProduct(x, y, x1, y0);
		float u = dotProduct(x, y, x0, y1);
		float v = dotProduct(x, y, x1, y1);
		float Sx = (3 - 2*(x-x0))*(x-x0)*(x-x0);
		float Sy = (3 - 2*(y-y0))*(y-y0)*(y-y0);
		float a = s + Sx*(t - s);
		float b = u + Sx*(v - u);
		float z = a + Sy*(b - a);
		return z;
	}
	public void perlinOctaves(float[][] map){
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				float total = 0.0f;
				float frequency = FREQUENCY;
				float amplitude = SIZE;
				for (int k = 0; k < OCTAVES; k++){
					total += perlinValue(((float) i)*frequency, ((float) j)*frequency)*amplitude;
					frequency *= LACUNARITY;
					amplitude *= PERSISTENCE;
				}
				map[i][j] = total;
			}
		}
	}
	public void normalize(float[][] map){
		float maximum = map[0][0];
		float minimum = map[0][0];
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				if (map[i][j] > maximum){maximum = map[i][j];}
				if (map[i][j] < minimum){minimum = map[i][j];}
			}
		}
		float OldRange = maximum - minimum;
		float NewRange = (float)2;
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				map[i][j] = (((map[i][j] - minimum)*NewRange)/OldRange) - 1;
			}
		}
	}
	public float[][] perlin2D(int seed, int size, int octaves, float persistence, 
			float lacunarity, float frequency){
		float[][] map = initializeValues(seed, size, octaves, persistence, lacunarity, frequency);
		initializePermutations();
		initializeGradients();
		perlinOctaves(map);
		normalize(map);
		return map;
	}
	
	public int[][] perlin2D(int seed, int size, int octaves, float persistence, 
			float lacunarity, float frequency, int amplitude){
		float[][] floats = perlin2D(seed, size, octaves, persistence, lacunarity, frequency);
		int[][] ints = new int[floats.length][floats[0].length];
		
		for(int x = 0; x < floats.length; x++){
			for(int y = 0; y < floats[0].length; y++){
				ints[x][y] = (int) (((floats[x][y]+1)/2.0)*amplitude);
			}
		}
		
		return ints;
	}
	
	public void draw(Graphics g, float[][] map){
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
//				Color acolor = new Color((int) (((map[i][j]+1)/2)*256));
//				g.setColor(acolor);
//				g.drawRect(i, j, 1, 1);
				
				/*Color acolor = new Color((int) ((((256*4+1) - map[i][j]+1)/2)*256*4));
				g.setColor(acolor);
				g.drawRect(i, j, 1, 1);*/
				
				Color acolor;
				if (map[i][j] > .8f && map[i][j] <= 1f){acolor = Color.WHITE;}
				else if (map[i][j] > .5f && map[i][j] <= .8f){acolor = Color.GRAY;}
				else if (map[i][j] > 0f && map[i][j] <= .5f){acolor = Color.GREEN;}
				else {acolor = Color.BLUE;}
				g.setColor(acolor);
				g.drawRect(i, j, 1, 1);
				
				/*Color acolor;
				if ((map[i][j] * 100) % 10 < .0001 || (map[i][j] * 100) % 10 > -.0001) {acolor = Color.BLACK;}
				else {acolor = Color.white;}
				g.setColor(acolor);
				g.drawRect(i, j, 1, 1);*/
				
				/*Color acolor;
				if (map[i][j] < 0f){acolor = Color.black;}
				else {acolor = new Color((int) (((map[i][j]+1)/2)*256));}
				g.setColor(acolor);
				g.drawRect(i, j, 1, 1);*/
				}
			}
	}
}
