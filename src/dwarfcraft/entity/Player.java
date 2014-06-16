package dwarfcraft.entity;

import component.control.InputComponent;
import component.control.PlayerInputComponent;
import component.graphics.GraphicsComponent;
import component.graphics.MobGraphicsComponent;
import component.physics.MobPhysicsComponent;
import component.physics.PhysicsComponent;

public class Player extends Entity {
	
	public Player(String name) {
		this(name, new PlayerInputComponent(), new MobPhysicsComponent(),
				new MobGraphicsComponent());
	}

	public Player(String name, InputComponent input, PhysicsComponent physics,
			GraphicsComponent graphics) {
		super(name, input, physics, graphics);
	}
}
