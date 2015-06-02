#!/bin/bash
#Chattie Client Launcher
#by demiurgosoft
dir=$(pwd);
#Client policy
policy="grant codeBase \"file:$dir/\" {
    permission java.security.AllPermission;
};"
echo $policy > client.policy;

#Change localhost according to server host ip
java -cp . -Djava.security.policy=client.policy -Djava.rmi.server.hostname=$(hostname -I) ChattieClient localhost 0

#if hostname -I doesnt work (or using MAC) try:
#alias myip="ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'"
