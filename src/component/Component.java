package component;

import dwarfcraft.entity.Entity;

public abstract class Component {
	protected Entity parent;
	
	public void setParentEntity(Entity e){
		this.parent = e;
	}
	
	public Entity getParentEntity(){
		return parent;
	}
}
