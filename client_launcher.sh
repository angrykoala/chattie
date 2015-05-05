#!/bin/bash
java -cp . -Djava.security.policy=server.policy ChatClient localhost 0
dir=$(pwd);

#java -cp . -Djava.rmi.server.codebase=$dir -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy ChatClient localhost 0
