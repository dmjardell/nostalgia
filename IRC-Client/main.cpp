#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <string.h>
#include <conio.h>
#include <process.h>
#include <iostream>
#include "sockets.h" //Hemmabygge: kolla här vid bedömning, likaså definitionerna i motsvarande .c filer
#include "console.h" //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
#include "functions.h"
#include "main.h"

#define WIN32_LEAN_AND_MEAN

int ThreadNr;
bool alive;
void ServerIO_Thread(void*);
cSocket sock;
cConsol con;
cUser usr;

int main(int argc, char *argv[])
{
 //   con.init();
 //   
 //   sock.init(6667,"irc.freequest.net");
 //   sock.conn();

	//sprintf(sock.send_buf,"NICK %s\r\n",NICK);
	//sock.sendData(sock.send_buf);
	//
	//sprintf(sock.send_buf,"USER %s localhost localhost :%s\r\n",USER,REAL);
	//sock.sendData(sock.send_buf);
	//
	//sprintf(sock.send_buf,"join %s\r\n",CHANNEL);
	//sock.sendData(sock.send_buf);
 //   
 //   _beginthread(ServerIO_Thread, 0, &ThreadNr);

	//_getch();
	
	con.init();
	con.addText("Projektarbete IRC-Klient. Enter /help for instructions. Remmember to set up your user variables with /set",105,true);
	con.draw();

	sprintf(usr.nick,"defaultusr");
	sprintf(usr.ident,"defaultusr");
	sprintf(usr.email,"defaultemail");

	while(1)
	{
		kbdInput();
	}


	return 0;
}

void ServerIO_Thread(void*)
{
    while(alive)
    {
	   sock.getData();
	   if(sock.len > 1)
	   {
            //con.addText(sock.recv_buf,sock.len,true);
			parseText(sock.recv_buf);
			//con.draw();
	   }
    }
	_endthread();
}
