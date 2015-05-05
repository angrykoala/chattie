#!/bin/bash
dir=$(pwd)

java -cp . -Djava.rmi.server.codebase=$dir -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy ChatServer
