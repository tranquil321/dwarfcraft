package services.graphics;

import java.awt.Color;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dwarfcraft.Entity;
import dwarfcraft.World;

public class GraphicsService2D extends JPanel implements GraphicsService {
	
	private World world;
	

	public GraphicsService2D(World world) {
		super();
		this.world = world;
		this.setBackground(Color.BLACK);
	}

	public void drawAll() {
		
	}

	public void drawEntity(Entity e) {
		
	}

	public void drawEntities(Collection<Entity> entities) {
		
	}
}
