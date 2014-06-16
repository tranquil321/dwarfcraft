package component.physics;

import java.awt.geom.Point2D;

import component.Component;
import dwarfcraft.world.World2D;

public abstract class PhysicsComponent extends Component {

	public Point2D.Double position = new Point2D.Double();
	public Point2D.Double velocity = new Point2D.Double();
	
	public abstract void setVelocityNorth(double speed);
	public abstract void setVelocityEast(double speed);
	public abstract void update(World2D world, long elapsed);
	
	public Point2D.Double getPosition() {
		return position;
	}
	public void setPosition(double x, double y) {
		this.position.x = x;
		this.position.y = y;
	}
	public Point2D.Double getVelocity() {
		return velocity;
	}
	public void setVelocity(double x, double y) {
		this.velocity.x = x;
		this.velocity.y = y;
	}
}
