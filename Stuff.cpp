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
class Graphics {
public:
	Graphics() {
		initscr();
		noecho();
		cbreak();
		keypad(stdscr, TRUE);
		curs_set(FALSE);
	}
	void draw(int x, int y, char sprite) {
		mvprintw(y, x, "%c", sprite);
	}
	void render() {
		refresh();
	}
	void stopGraphics() {
		endwin();
	}
};

//This service is responsible for getting input from the user.
class Keyboard {
public:
	Keyboard() {
		nodelay(stdscr, TRUE);
	}
	static bool hasNewKey() {
		int ch = getch();
		if (ch != ERR) {
			ungetch(ch);
			return TRUE;
		} else {
			return FALSE;
		}
	}
	static int getInput() {
		if (hasNewKey()) {
			return getch();
		} else {
			return 0;
		}
	}
	static int checkInput() {
		if (hasNewKey()) {
			int ch = getch();
			ungetch(ch);
			return ch;
		} else {
			return 0;
		}
	}
};

//The world class includes function calls to resolve collision errors, is responsible for
//updating the world, etc.
class World {
public:
	World(uint w, uint h) {
		WIDTH = w;
		HEIGHT = h;

		worldArray = (int**) malloc(sizeof(int) * w);

		for (int i = 0; i < HEIGHT; i++) {
			worldArray[i] = (int*) malloc(sizeof(int) * h);
			for (int j = 0; j < WIDTH; j++) {
				if (i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) {
					worldArray[i][j] = 1;
				} else {
					worldArray[i][j] = 0;
				}
			}
		}
	}
	~World() {
		delete[] worldArray;
	}

	//Returns true if the block is passable, otherwise returns false
	bool resolveCollision(int x, int y) {
		if (worldArray[x][y] == 0) {
			return true;
		} else
			return false;
	}
	void update(Graphics* graphics) {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (worldArray[i][j] == 1) {
					graphics->draw(i, j, '1');
				} else {
					graphics->draw(i, j, ' ');
				}
			}
		}
	}
private:
	int WIDTH;
	int HEIGHT;
	int** worldArray;
};

InputComponent::~InputComponent() {};
void InputComponent::update(GameObject* obj) = 0;

class PhysicsComponent {
public:
	virtual ~PhysicsComponent() {
	}
	;
	virtual void update(GameObject* obj, World* world) = 0;
};

class GraphicsComponent {
public:
	virtual ~GraphicsComponent() {
	}
	;
	virtual void update(GameObject* obj, Graphics* graphics) = 0;
};

class GameObject {
public:
	int x, y;
	int xv, yv;

	GameObject(InputComponent* input, PhysicsComponent* physics,
			GraphicsComponent* graphics) :
			input_(input), physics_(physics), graphics_(graphics) {
	}

	void update(World* world, Graphics* graphics) {
		input_->update(this);
		physics_->update(this, world);
		graphics_->update(this, graphics);
	}

private:
	InputComponent* input_;
	PhysicsComponent* physics_;
	GraphicsComponent* graphics_;
};

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

