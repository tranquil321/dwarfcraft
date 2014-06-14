package component.physics;

import java.awt.geom.Point2D;

public class MobPhysicsComponent extends PhysicsComponent {

	private Point2D.Double velocity;
	
	@Override
	public void setVelocityNorth(int speed) {
		velocity.y = speed;
	}

	@Override
	public void setVelocityEast(int speed) {
		velocity.x = speed;
	}

}
