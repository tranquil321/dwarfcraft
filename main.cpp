#include <stdlib.h>
#include <ncurses.h>
#include <unistd.h>
#include <stdio.h>
#include <iostream>

//The graphics service includes function calls to draw objects
class Graphics
{
public:
	void initialize(){
		initscr();
		noecho();
		curs_set(FALSE);
	}
	void draw( int x, int y, char sprite){
		mvprintw(x, y, "%c", sprite);
	}
	void refreshScreen(){
		refresh();
	}
};

//This service is responsible for getting input from the user.
class Keyboard
{
public:
	void initialize(){
		nodelay(stdscr,TRUE);
	}
	bool kbhit(){
		int ch = getch();
		if (ch != ERR){
			ungetch(ch);
			return TRUE;
		} else {
			return FALSE;
		}
	}
	static int getDirection(){
		return getch();
	}
};

//The world class includes function calls to resolve collision errors, is responsible for
//updating the world, etc.
class World
{
public:
	World(){
		WIDTH = 10;
		HEIGHT = 10;
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++){
				if (i==0 || i==9 || j==0 || j==9){
					worldArray[i][j] = 1;
				} else {
					worldArray[i][j] = 0;
				}
			}
		}
	}

	//Returns true if the block is passable, otherwise returns false
	bool resolveCollision(int x, int y){
		if (worldArray[x][y] == 0){
			return true;
		} 
		else return false;
	}
	void update(Graphics* graphics){
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++){
				if (worldArray[i][j] == 1){ graphics->draw(i,j,'1'); }
				else { graphics->draw(i,j,' '); }
			}
		}
	}
private:
	int WIDTH;
	int HEIGHT;
	int worldArray[10][10];
};



class GameObject;

class InputComponent
{
public:
	virtual ~InputComponent() {};
	virtual void update(GameObject* obj) = 0;
};

class PhysicsComponent
{
public:
	virtual ~PhysicsComponent() {};
	virtual void update(GameObject* obj, World* world) = 0;
};

class GraphicsComponent
{
public:
	virtual ~GraphicsComponent() {};
	virtual void update(GameObject* obj, Graphics* graphics) = 0;
};

class GameObject
{
public:
	int x,y;
	int xv,yv;
	
	GameObject(InputComponent* input,
			   PhysicsComponent* physics,
			   GraphicsComponent* graphics)
	: input_(input),
	  physics_(physics),
	  graphics_(graphics)
	{}
	
	void update(World* world, Graphics* graphics){
		input_->update(this);
		physics_->update(this, world);
		graphics_->update(this, graphics);
	}

private:
	InputComponent* input_;
	PhysicsComponent* physics_;
	GraphicsComponent* graphics_;
};



class PlayerInputComponent : public InputComponent
{
public:
	~PlayerInputComponent() {};
	void update(GameObject* obj){
		switch (Keyboard::getDirection()){
			case KEY_UP:
				obj->yv = -1;
				printf("Keyboard Hit");
				break;
			case KEY_DOWN:
				obj->yv = 1;
				break;
			case KEY_LEFT:
				obj->xv = -1;
				break;
			case KEY_RIGHT:
				obj->xv = 1;
				break;
		}
	}
};

class MobPhysicsComponent : public PhysicsComponent
{
public:
	~MobPhysicsComponent() {};
	void update(GameObject* obj, World* world){
		if (world->resolveCollision(obj->x + obj->xv, obj->y + obj->yv)){
			obj->x += obj->xv;
			obj->y += obj->yv;
		}
		obj->xv = 0;
		obj->yv = 0;
	}
};

class MobGraphicsComponent : public GraphicsComponent
{
public:
	~MobGraphicsComponent() {};
	MobGraphicsComponent(){
		spriteNormal = '@';
	}
	void update(GameObject* obj, Graphics* graphics){
		char sprite = spriteNormal;
		//if mob is hurt, change, etc.
		graphics->draw(obj->x, obj->y, sprite);
	}
private:
	char spriteNormal;
	//spriteHurt, spriteThirsty, etc.
	//additionally add support for blinking sprites
};

GameObject* createPlayer()
{
	return new GameObject(new PlayerInputComponent(), new MobPhysicsComponent(), new MobGraphicsComponent());
}

int main(int argc, char* argv[]){
	Graphics* graphics = new Graphics();
	graphics->initialize();
	
	World* world = new World();
	
	Keyboard* keyboard = new Keyboard();
	keyboard->initialize();
	
	GameObject* Player = createPlayer();
	Player->x = 5;
	Player->y = 5;
	
	while (true){
		world->update(graphics);
		Player->update(world, graphics);
		graphics->refreshScreen();
	}
		
}