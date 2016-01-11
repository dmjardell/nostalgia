/*Prototyp för socket.c klassen*/

#ifndef cSock
#define cSock
#include <windows.h>

class cSocket
{
      public:
      WSADATA wsaData;
      SOCKET sock;
      struct sockaddr_in sin;
      struct hostent* host;
      int buf_len;
      int iResult;
      int len;
      char recv_buf[512];
      char send_buf[3000];
      
      public:
      bool init(int, char*);
      void getData(void);
      void sendData(char*);
      bool conn(void);
	  void terminate(void);
};

#endif
