#ifndef cConsole
#define cConsole
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

class cConsol
{
    public:
    HANDLE wHnd;    // Output från programmet till användaren.
    HANDLE rHnd;    // Input från användare till konsolruta.
    SMALL_RECT windowSize, writeArea;
    COORD bufferSize, cursorPos, chPos;
    CHAR_INFO cChar[80*25];
	int lx,ly;
    
    
    public:
    void init(void);
    //void writeText(char[80*50], int);
	void clearText(void);
	void addText(char[80*25], int, bool);
	void draw(void);
	void moveText();
	void getWPos(void);
    
};
#endif
