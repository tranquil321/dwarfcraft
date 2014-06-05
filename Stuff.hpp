/*
 * Stuff.hpp
 *
 *  Created on: Jun 4, 2014
 *      Author: danimil
 */

#ifndef STUFF_HPP_
#define STUFF_HPP_

#include <ncurses.h>
#include <unistd.h>
#include <stdio.h>
#include <ctime>
#include <sys/time.h>
#include <iostream>

class Graphics {
public:
	Graphics();
	void draw(int x, int y, char sprite);
	void render();
	void stopGraphics();
};

class Keyboard {
public:
	Keyboard();
	static bool hasNewKey();
	static int getInput();
	static int checkInput();
};
class World {
public:
	World(uint w, uint h);
	~World();
	bool resolveCollision(int x, int y);
	void update(Graphics* graphics);
private:
	int WIDTH;
	int HEIGHT;
};

class GameObject;
class InputComponent {
public:
	virtual ~InputComponent();
	virtual void update(GameObject* obj);
};
class PhysicsComponent{
public:
	virtual ~PhysicsComponent();
	virtual void update(GameObject* obj, World* world);
};

class GraphicsComponent {
public:
	virtual ~GraphicsComponent();
	virtual void update(GameObject* obj, Graphics* graphics);
};


class GameObject {
public:
	int x, y;
	int xv, yv;

	GameObject(InputComponent* input, PhysicsComponent* physics,
			GraphicsComponent* graphics);
	void update(World* world, Graphics* graphics);

private:
	InputComponent* input_;
	PhysicsComponent* physics_;
	GraphicsComponent* graphics_;
};

GameObject* createPlayer();

#endif /* STUFF_HPP_ */
