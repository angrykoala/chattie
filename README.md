Chattie
=======
_by @demiurgosoft_    
[Chattie is in GitHub](https://github.com/demiurgosoft/chattie)
Simple Java application to deploy a simple chatting server easily using Java-rmi

##Instructions
1. Run `make` to compile both, Chattie server and client
2. Execute `rmiregistry &` if rmi registry is not running in your system
3. To run server, execute `bash server_launcher.sh` on classes/server folder
	* If not running on linux, modify server_launcher.sh ip manually: `-Djava.rmi.server.hostname=<<Your Ip>>`
4. To run client, execute `bash client_launcher.sh` on classes/client folder
	* If not running on linux, modify server_launcher.sh ip manually: `-Djava.rmi.server.hostname=<<Your Ip>>`
	* If Server is not running on localhost, modify manually the ip of where Chattie server is running: `ChattieClient <<Server Ip>> 0`

_Basic Chat functionality with simple java GUI_

_Currently, only direct connection is supported_

Chattie is under [GNU GP License](https://github.com/demiurgosoft/chattie/blob/master/LICENSE)

_chattie.png_ is under LGPL license by [Everaldo Coelho](http://icones.pro/es/pinguino-tux-chat-2-imagen-png.html) as tux_chat

##Troubleshooting
In case that Chattie is not working (Server or Client side) try some of the possible solutions:

* rmi error when executing server and/or client: make sure rmiregistry is working on the machine, ramiregistry should be executing in client and server machine
* ClassNotFoundException in Server side: try restarting rmiregistry
* Problem executing java application: To execute .class java, make sure you have jdk installed (if possible jdk >= 7)
* Server is running, but client doesn't connect
	* If the Sever doesn't show a message when user try to connect, make sure you have properly assigned the server ip to client (check instructions, step 4)
	* If the server shows a message, but client crash, make sure you have properly assigned the server & client ips (instructions, steps 3 & 4)
