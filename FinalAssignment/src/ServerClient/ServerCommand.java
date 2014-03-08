package ServerClient;

public class ServerCommand {
	String type_command;
	Object content;
	
	ServerCommand(String type, Object cont){
		type_command = type;
		content = cont;
	}
}
