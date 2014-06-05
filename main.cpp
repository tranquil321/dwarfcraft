
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <ctime>
#include <sys/time.h>

#import "Stuff.hpp"
	

int main(int argc, char* argv[]){
	Graphics* graphics = new Graphics();
	Keyboard* keyboard = new Keyboard();
	
	int worldWidth = 19;
	int worldHeight = 19;

	World* world = new World(worldWidth, worldHeight);
	
	GameObject* Player = createPlayer();
	Player->x = worldWidth/2;
	Player->y = worldHeight/2;
	
	//The game loop
	timeval previous, current;
	double elapsed;
	gettimeofday(&previous, NULL);
	const int FRAMES_PER_SECOND = 30;
	const int MS_PER_UPDATE = 50;
	double lag = 0.0;
	
	bool RUNNING = true;
		
	while (RUNNING) {
		gettimeofday(&current, NULL);
		elapsed = (current.tv_sec - previous.tv_sec) * 1000.0;
		elapsed += (current.tv_usec - previous.tv_usec) / 1000.0;
		previous = current;
		lag += elapsed;
		
		//Here is where an input update instruction would go, but I don't know how I
		//should handle this. Should I get rid of the calls to the keyboard service in
		//my player input controller and have it instead get all its data from here?
		//This input update step would check the input, and if it were a command to go to
		//a menu would exit the game loop and go to the menu loop. If it were not a menu
		//command, however, it would put the key-press back using ungetch, leaving it to
		//be processed by the various input controllers. I think that will work.
		if (Keyboard::checkInput() == '\n'){
			RUNNING = false;
		}	

		while (lag >= MS_PER_UPDATE) {
			world->update(graphics);
			Player->update(world, graphics);
			lag -= MS_PER_UPDATE;
		}
		
		graphics->render();
	}
	
	graphics->stopGraphics();	
	world->~World();
}
