package dwarfcraft;

import services.graphics.GraphicsService;
import component.control.InputComponent;
import component.physics.PhysicsComponent;

public abstract class Entity {
	int x, y;
	int xv, yv;
	
	InputComponent inputComponent;
	PhysicsComponent physicsComponent;
	GraphicsService graphicsComponent;

	public Entity(InputComponent input, PhysicsComponent physics,
			GraphicsService graphics){
		
	}
}
