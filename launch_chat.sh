#!/bin/bash
dir=$(pwd);
#Server policy
policy="grant codeBase \"file:$dir/\" {
    permission java.security.AllPermission;
};"
echo $policy > server.policy;

#Server launch
bash server_launcher.sh
#java -cp . -Djava.rmi.server.codebase=$dir -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy ChatServer
#Client launch
#java -cp . -Djava.security.policy=server.policy Cliente_Ejemplo localhost 0
