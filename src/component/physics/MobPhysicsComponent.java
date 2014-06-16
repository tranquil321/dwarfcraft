package component.physics;

import dwarfcraft.world.World2D;

public class MobPhysicsComponent extends PhysicsComponent {

	@Override
	public void setVelocityNorth(double speed) {
		velocity.y = speed;
	}

	@Override
	public void setVelocityEast(double speed) {
		velocity.x = speed;
	}

	@Override
	public void update(World2D world, long elapsed) {
		this.position.x += (elapsed/1000.0)*velocity.x;
		this.position.y += (elapsed/1000.0)*velocity.y;
	}
}
