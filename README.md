Chattie
=======
_by @demiurgosoft_    
[Chattie is in GitHub](https://github.com/demiurgosoft/chattie)    
Java application to deploy a simple chatting client-server easily using Java-rmi

##Instructions
1. Run `make` to compile both, Chattie server and client (will be placed under classes/ directory temporaly
	* Run `make client` to only compile client
	* Run `make server` to only compile server
2. Execute `rmiregistry &` if rmi registry is not running in your system
3. To run server, execute `bash server_launcher.sh` on classes/server folder
	* If not running on linux, modify server_launcher.sh ip manually: `-Djava.rmi.server.hostname=<<Your Ip>>`
4. To run client, execute `bash client_launcher.sh` on classes/client folder
	* If not running on linux, modify server_launcher.sh ip manually: `-Djava.rmi.server.hostname=<<Your Ip>>`
	* If Server is not running on localhost, modify manually the ip of where Chattie server is running: `ChattieClient <<Server Ip>> 0`

##Troubleshooting
In case that Chattie is not working (Server or Client side) try some of the possible solutions:

* _rmi error when executing server and/or client_: make sure rmiregistry is working on the machine, rmiregistry should be executing in client and server machine

* _ClassNotFoundException on Server side_: try restarting rmiregistry (it may took a couple of tries)

* _Problem executing java application_: To execute .class java, make sure you have jdk installed (if possible jdk >= 7)

* _Server is running, but client doesn't connect_:
	* Make sure your **username is correct**, it can't be already on use, also, server won't allow any variation of capital letters ("Jack,jack and jaCk won't be accepted at the same time), you can't chose the same name as the server name or names containing "server", also, server may have a banned list of names. Names with blank spaces can be used (Chattie will substitute for dashes, for example "Ford Prefect" will turn into "Ford_Prefect"), but anyway try to use a name without blank spaces to avoid problems with server.
	
	* If the Sever doesn't show a message when user try to connect, make sure you have properly assigned the server ip to client (check instructions, step 4)
	
	* If the server shows a message, but client crash, make sure you have properly assigned the server & client ips (instructions, steps 3 & 4)

* _Server disconnected suddenly while I was chatting_: don't worry, just restart the server and click on "File/Reconnect" on client to connect again

* _Client program closed unexpectedly without disconnecting from server_: Chattie Server will automatically kick out from chat any client which has connection problems, the client can reconnect in any moment automatically or clicking "File/Reconnect"

* _Some actions are disabled or can't be accessed_: Chattie is still under development, there are a lot of features not implemented yet, or not fully functional

-----------------------

_Currently, only direct connection is supported with basic All to All chat_

>Chattie has been tested on **Linux** (Debian), **Mac** (IOS) and **Raspberry pi** (Raspbian)

Chattie is under [GNU GP License](https://github.com/demiurgosoft/chattie/blob/master/LICENSE)

_chattie.png_ is under LGPL license by [Everaldo Coelho](http://icones.pro/es/pinguino-tux-chat-2-imagen-png.html) as tux_chat
