#!/bin/bash
dir=$(pwd);
#Client policy
policy="grant codeBase \"file:$dir/\" {
    permission java.security.AllPermission;
};"
echo $policy > client.policy;

java -cp . -Djava.security.policy=client.policy ChatClient localhost 0

#java -cp . -Djava.rmi.server.codebase=file:$dir/ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy ChatClient localhost 0
