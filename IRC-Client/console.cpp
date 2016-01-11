#include "console.h"

void cConsol::init(void)
{   
    wHnd = GetStdHandle(STD_OUTPUT_HANDLE);
    rHnd = GetStdHandle(STD_INPUT_HANDLE);

	//wHnd=CreateConsoleScreenBuffer(GENERIC_READ | GENERIC_WRITE, 0, NULL, CONSOLE_TEXTMODE_BUFFER, NULL);
	//SetConsoleActiveScreenBuffer(wHnd);
 //   
    windowSize.Left= 0;
    windowSize.Top= 0;
    windowSize.Right = 79;
    windowSize.Bottom = 24;
    writeArea.Left= 0;
    writeArea.Top= 0;
    writeArea.Right = 79;
    writeArea.Bottom = 23;
    bufferSize.X = 80;
    bufferSize.Y = 25;
    cursorPos.X = 0;
    cursorPos.Y = 24;
    chPos.X = 0;//30
    chPos.Y = 0;//30
	lx = 0;
	ly = 0;
    
    SetConsoleWindowInfo(wHnd, TRUE, &windowSize);
    SetConsoleTitle("OOB Chat - David Mj\x84rdell");   
    SetConsoleCursorPosition(wHnd, cursorPos);
    SetConsoleScreenBufferSize(wHnd, bufferSize);
	SetConsoleOutputCP(1143);
	memset(cChar,32, sizeof(cChar));
}

void cConsol::addText(char msg[80*25], int len, bool shift)
{

	int x,y;
	y=22-(len/80);
	x=0;
	if (shift==true) moveText();
	 
	for (int l=0;l<(len);l++,x++)
	{
		 if (msg[l]!= 13) cChar[x+80*y].Char.AsciiChar=msg[l],cChar[x+80*y].Attributes=FOREGROUND_RED|FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY|BACKGROUND_BLUE;

		 if(msg[l]== 13) {moveText();x=0;};
		 if(x > 0 && (x % 80 == 0)){x=0;y++;}
	}

}
void cConsol::clearText(void)
{
	for(int i=0;i <(80*25) ;i++)
	{
		cChar[i].Char.AsciiChar=' ';
		cChar[i].Attributes=FOREGROUND_RED|FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY|BACKGROUND_BLUE;
	}
}

void cConsol::draw(void)
{
	WriteConsoleOutput(wHnd, cChar, bufferSize, chPos, &writeArea);
}

void cConsol::moveText()
{

	int lx,ly;
	for(ly=0;ly<24;ly++)
	{
		for(lx=0;lx<80;lx++)
		{
			cChar[lx+80*ly].Char.AsciiChar = cChar[lx+80*(ly+1)].Char.AsciiChar;
			cChar[lx+80*ly].Attributes=FOREGROUND_RED|FOREGROUND_BLUE|FOREGROUND_GREEN|FOREGROUND_INTENSITY|BACKGROUND_BLUE;
			if (ly==23) cChar[lx+80*(ly+1)].Char.AsciiChar =' ';
		}
	}
 }


//	int x,y,i;
//	
//	if( len < 80) {  len = 80;}
//
//	for(i=0;i < (len/80); i++)
//	{
//		for(y = 0; y < 49; y++)
//		{
//			for(x = 0; x < 80; x++)
//			{
//				cChar[x+80*y].Char.AsciiChar = cChar[x+80*(y+1)].Char.AsciiChar;
//				if (y==49) cChar[x+80*(y+1)].Char.AsciiChar = 0;
//			}
//		}
//	}
//	if(ly>50){ly=0;}
//	else { ly - len/80;}

void cConsol::getWPos(void)
{
	//lx = 0; ly = 0;
	while(cChar[lx+80*ly].Char.AsciiChar != 0)
	{
		if(cChar[lx+80*ly].Char.AsciiChar == 13 || cChar[lx+80*ly].Char.AsciiChar == 10)
		{
			ly++;
		}
		if( (lx % 80) == 0 && lx > 0)
		{
			ly++;
		}
		lx++;
	}
}