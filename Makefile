# Makefile
CPPFLAGS=-g -I/usr/include/ncurses
LDFLAGS=-g -lncurses

main: main.o
	clang++ $(LDFLAGS) -o main main.o
main.o: main.cpp
	clang++ $(CPPFLAGS) -c main.cpp
clean:
	rm -f main.o