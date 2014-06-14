package dwarfcraft;

import java.util.ArrayList;

public class World3D extends World<ArrayList<ArrayList<Integer>>> {

	public World3D(int worldWidth, int worldHeight) {

		this.worldData = new ArrayList<ArrayList<Integer>>(worldWidth);
		for (int x = 0; x < worldWidth; x++) {
			this.worldData.add(new ArrayList<Integer>(worldHeight));
			for (int y = 0; y < worldWidth; y++) {
				this.worldData.get(x).add(0);
			}
		}
	}

}
