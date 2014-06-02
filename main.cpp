class World
{
public:
	//Returns true if the block is passable, otherwise returns false
	bool resolveCollision(int x, int y){
		if (worldArray[x][y] == 0){
			return true;
		} 
		else return false;
	}
private:
	int worldArray[10][10] = {{1,1,1,1,1,1,1,1,1,1},
							  {1,0,0,0,0,0,0,0,0,1},
							  {1,0,0,0,0,0,0,0,0,1},
							  {1,0,0,1,1,0,0,0,0,1},
							  {1,0,0,1,0,0,0,1,0,1},
							  {1,1,0,0,0,0,0,1,0,1},
							  {1,0,0,0,0,0,0,0,0,1},
							  {1,0,0,0,1,1,0,0,0,1},
							  {1,0,0,0,0,0,0,0,0,1},
							  {1,1,1,1,1,1,1,1,1,1}};
}

class Graphics

class Controller

class InputComponent
{
public:
	virtual ~InputComponent() {}
	virtual void update(GameObject& obj) = 0;
};

class PhysicsComponent
{
public:
	virtual ~PhysicsComponent() {}
	virtual void update(GameObject& obj, World& world) = 0;
};

class GraphicsComponent
{
public:
	virtual ~GraphicsComponent() {}
	virtual void update(GameObject& obj, Graphics& graphics) = 0;
};

class GameObject
{
public:
	int x,y;
	int xv,yv;
	
	Gameobject(InputComponent* input,
			   PhysicsComponent* physics,
			   GraphicsComponent* graphics)
	: input_(input),
	  physics_(physics),
	  graphics_(graphics)
	{}
	
	void update(World& world, Graphics& graphics){
		input_->update(*this);
		physics_->update(*this, world);
		graphics_->update(*this, graphics);
	}

private:
	InputComponent* input_;
	PhysicsComponent* physics_;
	graphicsComponent* graphics_;
};



class PlayerInputComponent : public InputComponent
{
public:
	virtual void update(GameObject& obj){
		switch (Controller::getDirection()){
			case W:
				player.yVelocity = -1;
				break;
			case S:
				player.yVelocity += 1;
				break;
			case A:
				player.xVelocity = -1;
				break;
			case D:
				player.xVelocity = 1;
				break;
		}
	}
}

class MobPhysicsComponent : public PhysicsComponent
{
	virtual void update(GameObject& obj, World& world){
		if (world.resolveCollision(obj.x + obj.xv, obj.y + obj.yv)){
			obj.x += obj.xv;
			obj.y += obj.yv;
		}
		obj.xv = 0;
		obj.yv = 0;
	}
}

class MobGraphicsComponent : public GraphicsComponent
{
public:
	virtual void update(GameObject& obj, Graphics& graphics){
		char* sprite = spriteNormal;
		//if mob is hurt, change, etc.
		graphics.draw(sprite, player.x, player.y)
	}
private:
	char* spriteNormal = '@';
	//spriteHurt, spriteThirsty, etc.
	//additionally add support for blinking sprites
}

GameObject* createPlayer()
{
	return new GameObject(new PlayerInputComponent(),
						  new MobPhysicsComponent(),
						  new MobGraphicsComponent());
}