package component.physics;

import java.awt.geom.Point2D;

import component.Component;
import dwarfcraft.entity.Entity;

public abstract class PhysicsComponent extends Component {

	private Point2D.Double velocity;
	
	public abstract void setVelocityNorth(int speed);
	public abstract void setVelocityEast(int speed);
	
}
