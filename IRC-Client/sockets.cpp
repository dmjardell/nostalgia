/*Egengjord class för WIN32 Socket API:n med syfte att förenkla huvudprogrammet samt underlätta hanteringen av framtida trådar*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>
#include "sockets.h"

bool cSocket::init(int port, char* server)
{
	//Initiera WS2_32.dll och kolla så rätt version existerar
	iResult = WSAStartup(MAKEWORD(2,2), &wsaData);
	if (iResult != 0)
	{
		printf("WSAStartup failed: %d\n", iResult);
		return FALSE;
	}


	//Skapa socketen
	sock = INVALID_SOCKET;
	sock = socket(AF_INET,SOCK_STREAM,0);

	if (sock == INVALID_SOCKET)
	{
		printf("Error at socket(): %ld\n", WSAGetLastError());
		WSACleanup();
		closesocket( sock );
		return FALSE;
	}
	//skapa strukturen host som håller värdena från gethostbyname()
	if((host = gethostbyname(server)) == NULL)
	{
		printf("gethostbyname() failed");
		WSACleanup();
		closesocket( sock );
		return FALSE;
	}

	memset( &sin, 0, sizeof(sin) );

	sin.sin_family = AF_INET;
	sin.sin_addr.s_addr = ((struct in_addr *)(host->h_addr))->s_addr;
	sin.sin_port = htons( port );
	
	return TRUE;
}

bool cSocket::conn(void)
{
     	//anslut till ADDR med socketen
	if ( connect( sock, (struct sockaddr *) &sin, sizeof(sin) ) == SOCKET_ERROR )
	{
		printf("Could not connect to server");
		WSACleanup();
		closesocket( sock );
		return FALSE;
	}
	return TRUE;
}

void cSocket::sendData(char *msg)
{
     send(sock, msg,strlen(msg),0);
     //printf(send_buf);
      memset(send_buf,'\0',sizeof(send_buf));
}

void cSocket::getData(void)
{
     memset(recv_buf,'\0',sizeof(recv_buf));
     if((len = recv(sock, recv_buf, sizeof(recv_buf),0))>1)//nyändring kolla om det felar
     {
        //Om ett PING blir skickat till klienten så svarar man med PONG för att hålla kontakten med IRC-servern. Detta måste ske med jämna mellanrum.
        if(!_strnicmp(recv_buf,"PING",4))
        {
            recv_buf[1]='O';
            sendData(recv_buf);
			memset(recv_buf,0,sizeof(recv_buf));

        }
    }
      
}

void cSocket::terminate(void)
{
	WSACleanup();
	closesocket(sock);
}
     
