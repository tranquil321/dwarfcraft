#include <stdlib.h>
#include <ncurses.h>
#include <unistd.h>
#include <stdio.h>
#include <ctime>
#include <sys/time.h>
#include <iostream>

//The graphics service includes function calls to draw objects
class Graphics
{
public:
	void initialize(){
		printf("Initializing\n");
		initscr();
		noecho();
		curs_set(0);
	}
	void draw( int x, int y, char sprite){
		mvprintw(x, y, "%c", sprite);
	}
	void render(){
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
		printf("Get direction\n");
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
				printf("Key Up.\n");
				obj->yv = -1;
				break;
			case KEY_DOWN:
				printf("Key Down.\n");
				obj->yv = 1;
				break;
			case KEY_LEFT:
				printf("Key Left.\n");
				obj->xv = -1;
				break;
			case KEY_RIGHT:
				printf("Key Right.\n");
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
		printf("%d, %d\n",obj->x, obj->y);
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
		printf("Drew character\n");
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
		
	Keyboard* keyboard = new Keyboard();
	keyboard->initialize();
	
	World* world = new World();
	
	GameObject* Player = createPlayer();
	Player->x = 5;
	Player->y = 5;
	
	//The game loop
	timeval previous, current;
	double elapsed;
	gettimeofday(&previous, NULL);
	const int FRAMES_PER_SECOND = 30;
	const int MS_PER_UPDATE = 50;
	double lag = 0.0;
	
	while (true) {
		gettimeofday(&current, NULL);
		elapsed = (current.tv_sec - previous.tv_sec) * 1000.0;
		elapsed += (current.tv_usec - previous.tv_usec) / 1000.0;
		previous = current;
		lag += elapsed;
		
		while (lag >= MS_PER_UPDATE) {
			world->update(graphics);
			Player->update(world, graphics);
			lag -= MS_PER_UPDATE;
			printf("%lf\n", elapsed);
		}
		
		graphics->render();
	}
		
}