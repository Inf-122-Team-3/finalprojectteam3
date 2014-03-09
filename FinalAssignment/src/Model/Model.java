package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.Player;

public class Model {
	
	private Board board;
	private Map<Player, List<Message>> messages;
	
	public Model(){
		
	}
	
	public Model(Board b){
		board = b;
		messages = new HashMap<Player, List<Message>>();
	}

	public Board getBoard() {
		return board;
	}

	public List<Message> getMessages(Player p) {
		return messages.get(p);
	}
	
	public Map<Player, List<Message>> getAllMessages(){
		return messages;
	}

	public void addMessage(Message m, Player p) {
		if(!messages.containsKey(p))
			messages.put(p, new ArrayList<Message>());
		messages.get(p).add(m);
	}
	
	public void addMessageToAll(Message m, List<Player> players){
		for(Player p : players)
			addMessage(m, p);
	}
	
	public void clearMessages(){
		messages.clear();
	}
}
