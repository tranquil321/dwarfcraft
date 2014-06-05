# Makefile
CPPFLAGS=-g -I/usr/include/ncurses
LDFLAGS=-g -lncurses 

all: main

main: main.o stuff.o
	clang++ $(LDFLAGS) -o main main.o stuff.o
main.o: main.cpp
	clang++ $(CPPFLAGS) -c main.cpp
stuff.o: stuff.cpp
	clang++ $(CPPFLAGS) -c Stuff.cpp
clean:
	rm -rf *.o main bin/ 