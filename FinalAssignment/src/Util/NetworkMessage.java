package Util;

import java.util.Vector;

import com.google.gson.Gson;

import Model.Model;

public class NetworkMessage {
	int id_player;
	Model _model = null;
	Vector<Command> commands;
	
	public NetworkMessage(){
		this.commands = new Vector<>();
	}
	
	public NetworkMessage(int id, Model model){
		setModel(model);
		this.commands = new Vector<>();
	}
	
	public NetworkMessage(String jsonString){
		Gson gson = new Gson();
		  
		NetworkMessage n1 = gson.fromJson(jsonString, NetworkMessage.class);
		
		setModel(n1.getModel());
		setCommands(n1.getCommands());
	}
	
	public void addCommand(Command command){
		this.commands.add(command);
	}
	
	public Vector<Command> getCommands(){
		return this.commands;
	}
	
	public void setCommands(Vector<Command> cs){
		this.commands = cs;
	}
	
	public void setModel(Model m){
		this._model = m;
	}
	
	public Model getModel(){
		return this._model;
	}
	
	public String toJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public void setIdPlayer(int id){
		this.id_player = id;
	}
}
