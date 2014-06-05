/*
 * Stuff.cpp
 *
 *  Created on: Jun 4, 2014
 *      Author: danimil
 */

#include <stdlib.h>
#include <ncurses.h>
#include <unistd.h>
#include <stdio.h>
#include <ctime>
#include <sys/time.h>
#include <iostream>

#include "Stuff.hpp"

//The graphics service includes function calls to draw objects
Graphics::Graphics() {
	initscr();
	noecho();
	cbreak();
	keypad(stdscr, TRUE);
	curs_set(FALSE);
}
void Graphics::draw(int x, int y, char sprite) {
	mvprintw(y, x, "%c", sprite);
}
void Graphics::render() {
	refresh();
}
void Graphics::stopGraphics() {
	endwin();
}

//This service is responsible for getting input from the user.
Keyboard::Keyboard() {
	nodelay(stdscr, TRUE);
}
bool Keyboard::hasNewKey() {
	int ch = getch();
	if (ch != ERR) {
		ungetch(ch);
		return TRUE;
	} else {
		return FALSE;
	}
}
int Keyboard::getInput() {
	if (hasNewKey()) {
		return getch();
	} else {
		return 0;
	}
}
int Keyboard::checkInput() {
	if (hasNewKey()) {
		int ch = getch();
		ungetch(ch);
		return ch;
	} else {
		return 0;
	}
}

//The world class includes function calls to resolve collision errors, is responsible for
//updating the world, etc.
World::World(uint w, uint h) {
	WIDTH = w;
	HEIGHT = h;

	this->worldArray = (int**) malloc(sizeof(int) * w);

	for (int i = 0; i < HEIGHT; i++) {
		worldArray[i] = (int*) malloc(sizeof(int) * h);
		for (int j = 0; j < WIDTH; j++) {
			if (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) {
				worldArray[i][j] = 1;
			} else if(i == 1) {
				worldArray[i][j] = 2;
			} else {
				worldArray[i][j] = 0;
			}
		}
	}
}
World::~World() {
	delete[] worldArray;
}

//Returns true if the block is passable, otherwise returns false
bool World::resolveCollision(int x, int y) {
	if (worldArray[x][y] == 0) {
		return true;
	} else
		return false;
}
void World::update(Graphics* graphics) {
	for (int i = 0; i < HEIGHT; i++) {
		for (int j = 0; j < WIDTH; j++) {
			switch(worldArray[i][j]) {
			case 1:
				graphics->draw(i, j, '#');
				break;
			case 0:
				graphics->draw(i, j, ' ');
				break;
			case 2:
				graphics->draw(i, j, '^');
				break;
			}
		}
	}
}


InputComponent::~InputComponent(){};
PhysicsComponent::~PhysicsComponent(){};
GraphicsComponent::~GraphicsComponent(){};
void GraphicsComponent::update(GameObject* obj, Graphics* graphics){};




GameObject::GameObject(InputComponent* input, PhysicsComponent* physics,
		GraphicsComponent* graphics) :
		input_(input), physics_(physics), graphics_(graphics) {
}

void GameObject::update(World* world, Graphics* graphics) {
	input_->update(this);
	physics_->update(this, world);
	graphics_->update(this, graphics);
}

class PlayerInputComponent: public InputComponent {
public:
	~PlayerInputComponent() {
	}
	;
	void update(GameObject* obj) {
//		mvprintw(0,11,"Player Input Component Update");
		switch (Keyboard::getInput()) {
		case KEY_UP:
			obj->yv = -1;
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

class MobPhysicsComponent: public PhysicsComponent {
public:
	~MobPhysicsComponent() {
	}
	;
	void update(GameObject* obj, World* world) {
		if (world->resolveCollision(obj->x + obj->xv, obj->y + obj->yv)) {
			obj->x += obj->xv;
			obj->y += obj->yv;
		}
		obj->xv = 0;
		obj->yv = 0;
	}
};

class MobGraphicsComponent: public GraphicsComponent {
public:
	~MobGraphicsComponent() {
	}
	;
	MobGraphicsComponent() {
		spriteNormal = '@';
	}
	void update(GameObject* obj, Graphics* graphics) {
		char sprite = spriteNormal;
		//if mob is hurt, change, etc.
		graphics->draw(obj->x, obj->y, sprite);
	}
private:
	char spriteNormal;
	//spriteHurt, spriteThirsty, etc.
	//additionally add support for blinking sprites
};

GameObject* createPlayer() {
	return new GameObject(new PlayerInputComponent(), new MobPhysicsComponent(),
			new MobGraphicsComponent());
}

