package services.graphics;

import java.util.Collection;

import dwarfcraft.entity.Entity;

public interface GraphicsService {
	public void drawAll();
	public void drawEntity(Entity e);
	public void drawEntities(Collection<Entity> entities);
	public void render();
}
