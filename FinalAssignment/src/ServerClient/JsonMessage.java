package ServerClient;

import java.util.Vector;

import Model.Model;

public class JsonMessage {
	String id = null;
	Model _model = null;
	Vector<ServerCommand> commands;
	
	JsonMessage(){
		commands = new Vector<>();
	}
	
	JsonMessage(String id, Model model){
		_model = model;
		commands = new Vector<>();
	}
	
	void addCommand(ServerCommand command){
		commands.add(command);
	}
	
	Vector<ServerCommand> getCommands(){
		return commands;
	}
}
