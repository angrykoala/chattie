#Chattie Makefile
#by demiurgosoft

#Flags
COMPILER=javac
LINKER=jar

#Directories
SDIR=src
GUISDIR=$(SDIR)/gui
CDIR=classes
BDIR=bin
CLIENTDIR=$(CDIR)/client
SERVERDIR=$(CDIR)/server


_CLIENT=ServerInterface.java ChattieClient.java ClientInterface.java ChatMessage.java Chat.java Login.java gui/ClientGUI.java gui/LoginGUI.java
CLIENT_CLASS=$(patsubst %,$(CLIENTDIR)/%,$(_CLIENT:.java=.class))
#CLIENT_CLASS2=$(patsubst %,-C $(CDIR) %,$(_CLIENT:.java=.class))
_SERVER=ServerInterface.java ChatServer.java ClientInterface.java ChatMessage.java
SERVER_CLASS=$(patsubst %,$(SERVERDIR)/%,$(_SERVER:.java=.class))
#SERVER_CLASS2=$(patsubst %,-C $(CDIR) %,$(_SERVER:.java=.class))
#CLIENT_BIN=$(CLIENTDIR)/ChattyClient.jar
#SERVER_BIN=$(SERVERDIR)/ChattyServer.jar
ICON=chattie.png

.PHONY: all
all: client server

.PHONY: client
client: $(CLIENTDIR)\/ $(CLIENT_CLASS) $(CLIENTDIR)/client_launcher.sh
	cp $(ICON) $(CLIENTDIR)

.PHONY: server
server: $(SERVERDIR)\/ $(SERVER_CLASS) $(SERVERDIR)/server_launcher.sh
#$(CLIENT_BIN): $(CLIENT_CLASS)
#	$(LINKER) cfe $@ ChatClient $(CLIENT_CLASS2)
	
#$(SERVER_BIN): $(SERVER_CLASS)
#	$(LINKER) cfe $@ ChatServer $(SERVER_CLASS2)

#compile class
$(SERVERDIR)/%.class: $(SDIR)/%.java
	$(COMPILER) -d $(SERVERDIR) $^ -sourcepath $(SDIR)
$(CLIENTDIR)/%.class: $(SDIR)/%.java
	$(COMPILER) -d $(CLIENTDIR) $^ -sourcepath $(SDIR):$(GUISDIR)
$(CLIENTDIR)/gui/%.class: $(SDIR)/gui/%.java
	$(COMPILER) -d $(CLIENTDIR)/gui $^ -sourcepath $(SDIR):$(GUISDIR)
$(CLIENTDIR)/%.sh: launchers/%.sh
	cp $< $@
$(SERVERDIR)/%.sh: launchers/%.sh
	cp $< $@


%/:
	mkdir -p $@



.PHONY: astyle
astyle:
	astyle --style=java --break-closing-brackets --align-pointer=name --delete-empty-lines --indent-col1-comments --unpad-paren -n -Q $(SDIR)/*.java $(SDIR)/*/*.java
.PHONY: clean
clean:
	rm -r -f  $(CDIR) *~ $(SDIR)/*~ *.class *.jar
.PHONY: print-%
print-%  : ; @echo $* = $($*)
