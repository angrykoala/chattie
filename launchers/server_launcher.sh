#!/bin/bash
dir=$(pwd);
#Server policy
policy="grant codeBase \"file:$dir/\" {
    permission java.security.AllPermission;
};"
echo $policy > server.policy;

java -cp . -Djava.rmi.server.codebase=file:$dir/ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy ChatServer
