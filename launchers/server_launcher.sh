#!/bin/bash
dir=$(pwd);
#Server policy
policy="grant codeBase \"file:$dir/\" {
    permission java.security.AllPermission;
};"
echo $policy > server.policy;

java -cp . -Djava.rmi.server.codebase=file:$dir/ -Djava.rmi.server.hostname=$(hostname -I) -Djava.security.policy=server.policy ChatServer

#if hostname doesnt work
#alias myip="ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'"
