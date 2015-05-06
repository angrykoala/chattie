Chattie
=======
Simple Java application to deploy a simple chatting server easily using Java-rmi

##Instructions
1. Compile running `make`
2. Execute rmi registry `rmiregistry &`
3. Execute chat launcher `bash launch_chat.sh`
	* This will generate server.policy file and will execute chattie server (executing server_launcher.sh)
4. Execute chat client `bash client_launcher.sh`
>Once server.policy is generated, server\_launcher.sh can be used instead of launch\_chat.sh to execute server (unless program location is changed)

_Currentlly only local connection from client to server functionalities are working_


Chattie is under [GNU GP License](https://github.com/demiurgosoft/chattie/blob/master/LICENSE)
