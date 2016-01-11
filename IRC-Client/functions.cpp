#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>
#include <process.h>
#include <time.h>
#include "functions.h"
#include "sockets.h"
#include "main.h"
#include "console.h"

extern cSocket sock;
extern void ServerIO_Thread(void*);
extern int ThreadNr;
extern cUser usr;
extern cConsol con;
extern bool alive;

struct tm *cTime;
time_t rTime;

void kbdInput(void)
{
	char input[256];
	char tmp[512];
	gets(input);
	if(input[0] == '/')
	{
		usrCmd(input);
		return;
	}
	else{
	time(&rTime);
	cTime = localtime(&rTime);
	sprintf(tmp,"PRIVMSG %s :%s\r\n",usr.channel,input);
	sock.sendData(tmp);
	sprintf(tmp,"%d:%d <%s> %s",cTime->tm_hour,cTime->tm_min,usr.nick,input);
	con.addText(tmp,strlen(tmp),true);
	con.draw();
	}
}

void usrCmd(char cmd[256])
{
	
	char* prefix;
	char* arg;
	char* arg2;
	char* arg3;
	char tmp[512];
	
	prefix = strtok(cmd," ");
	arg = strtok(NULL," ");
	arg2 = strtok(NULL," ");
	arg3 = strtok(NULL," ");

	if(!strncmp(prefix,"/connect",8) && arg != NULL && arg2 != NULL)
	{
		if(alive)
		{
			con.addText("Already connected to a server, use /disconnect before you try another connection attempt.",89,true);
			con.draw();
			return;
		}
		sock.init(atoi(arg2),arg);
		sock.conn();
		alive = true;
		_beginthread(ServerIO_Thread, 0, &ThreadNr);

		sprintf(sock.send_buf,"NICK %s\r\n",usr.nick);
		sock.sendData(sock.send_buf);
		sprintf(sock.send_buf,"USER %s localhost localhost :%s\r\n",usr.ident,usr.email);
		sock.sendData(sock.send_buf);
	}
	else if(!strncmp(prefix,"/join",5) && arg != NULL)
	{
		strcpy(usr.channel,arg);
		sprintf(sock.send_buf,"join %s\r\n",usr.channel);
		con.clearText();
		sock.sendData(sock.send_buf);
	}else if(!strncmp(prefix,"/clear",6)){
		con.clearText();
		con.draw();
	}else if(!strncmp(prefix,"/help",5)){
		con.clearText();
		con.addText("/connect <server> <port> eg. /connect irc.freequest.net 6667",60,true);
		con.addText("/clear Will clear the screen of anything present on it",54,true);
		con.addText("/join #channelname will join the channel named channelname",58,true);
		con.addText("/name Will show active users in the selected channel", 52, true);
		con.addText("/disconnect Will close the connection to the server you're currently connected to", 81, true);
		con.addText("/part Will leave your active channel", 36, true);
		con.addText("/set <nick> <wanted nickname> eg /set nick JohnDoe", 50, true);
		con.addText("/raw Used for sending a raw IRC message. eg. /raw PRIVMSG #channel :hello", 73, true);
		con.draw();
	}else if(!strncmp(prefix,"/name",5)){
		sprintf(tmp,"NAMES %s\r\n",usr.channel);	
		con.addText("Online users in active channel:", 31, true);
		con.addText(" ", 1, true);
		con.draw();

		sock.sendData(tmp);
	}else if(!strncmp(prefix,"/disconnect",10)){
		con.clearText();
		con.addText("Disconnecting...",16,true);
		con.addText("Closing worker thread...",24,true);
		con.addText("Success! /help for available commands.",38,true);
		con.draw();
		sock.sendData("QUIT :BARBARIANS FFFFFFFFFFUUUU- GOTTA GO\r\n");
		alive = false;
	}else if(!strncmp(prefix,"/part",5)){
		con.clearText();
		con.addText("You have left your active channel, what do you want to do now? Enter /help for a list of available commands.",108,true); // ACK OCH VE för att räkna bokstäver.
		con.draw();
		sprintf(tmp,"PART %s\r\n",usr.channel);
		sock.sendData(tmp);
	}else if(!strncmp(prefix,"/set",4) && arg != NULL && arg2 != NULL){
		if(!strncmp(arg,"nick",4))
		{
			sprintf(usr.nick,"%s",arg2);
			sprintf(tmp,"Nickname set to: %s",usr.nick);
			if(alive)
			{
				sprintf(sock.send_buf,"NICK %s\r\n",usr.nick);
				sock.sendData(sock.send_buf);
			}
			con.addText(tmp,strlen(tmp),true);
			con.draw();
		}
	}else if(!strncmp(prefix,"/raw",4) && arg != NULL){
		sprintf(tmp,"%s\r\n",arg);
		sock.sendData(tmp);
	}
	else{
		con.addText("Invalid command, use /help for commands available to you",56,true);
		con.draw();
	}

}

void parseText(char text[3000])
{
	int ichar = 0;
	char line[1000];
	memset(line,0,sizeof(line));

	for(unsigned int i = 0; i < strlen(text);i++)
	{
		line[ichar] = text[i];
		ichar++;
		if(text[i] == 13)
		{
			ichar = 0;
			parseLine(line);
			memset(line,0,sizeof(line));
		}
	}
}

void parseLine(char line[1000])
{
	int count=0,ichar=0,ichar2=0, count2=0;
	char message[1000];
	char name[50];
	char lineout[1050];

	memset(lineout,0,sizeof(lineout));
	memset(name,0,sizeof(name));
	memset(message,0,sizeof(message));
	

	for(unsigned int i = 0; i < strlen(line); i++)
	{
		if(line[i] == '!' || line[i] == ' ') count2++;

		if(count >= 1 && count <= 2 && count2 < 1)
		{
			name[ichar2] = line[i];
			ichar2++;
		}

		if(line[i] == ':') count++;

		if(count >= 2)
		{
			message[ichar] = line[i+1];
			ichar++;
		}
	}
	if(strlen(name) > 1 && strlen(message) > 1)
	{
		time(&rTime);
		cTime = localtime(&rTime);
		sprintf(lineout,"%d:%d <%s> %s",cTime->tm_hour,cTime->tm_min,name,message);
		con.addText(lineout,strlen(lineout),true);
		con.draw();
	}
}