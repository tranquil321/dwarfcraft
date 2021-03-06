package dwarfcraft.entity;

import java.awt.geom.Point2D;

import services.graphics.GraphicsService;
import services.graphics.GraphicsService2D;
import component.control.InputComponent;
import component.control.PlayerInputComponent;
import component.graphics.GraphicsComponent;
import component.graphics.MobGraphicsComponent;
import component.physics.MobPhysicsComponent;
import component.physics.PhysicsComponent;
import dwarfcraft.world.World2D;

public class Entity {
	
	String name = "";
	private InputComponent inputComponent;
	private PhysicsComponent physicsComponent;
	private GraphicsComponent graphicsComponent;

	public Entity(String name, InputComponent input, PhysicsComponent physics,
			GraphicsComponent graphics) {
		this.name = name;
		inputComponent = input;
		inputComponent.setParentEntity(this);
		physicsComponent = physics;
		physicsComponent.setParentEntity(this);
		graphicsComponent = graphics;
		graphicsComponent.setParentEntity(this);
	}

	public Point2D.Double getPosition() {
		return physicsComponent.getPosition();
	}

	public void setPosition(double x, double y) {
		physicsComponent.setPosition(x, y);
	}

	public InputComponent getInputComponent() {
		return inputComponent;
	}

	public void setInputComponent(InputComponent inputComponent) {
		this.inputComponent = inputComponent;
	}

	public PhysicsComponent getPhysicsComponent() {
		return physicsComponent;
	}

	public void setPhysicsComponent(PhysicsComponent physicsComponent) {
		this.physicsComponent = physicsComponent;
	}

	public GraphicsComponent getGraphicsComponent() {
		return graphicsComponent;
	}

	public void setGraphicsComponent(GraphicsComponent graphicsComponent) {
		this.graphicsComponent = graphicsComponent;
	}

	public void update(World2D world, GraphicsService2D graphics, long elapsed) {
		inputComponent.update();
		physicsComponent.update(world, elapsed);
	}
}
