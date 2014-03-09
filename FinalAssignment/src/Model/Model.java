package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.Player;

/**
 * The overall Model of the Board Game Simulation. Contains all the necessary parts that need to be communicated to the client
 *
 */
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

	/**
	 * adds a Message to the List of messages for Player p.
	 * @param m the Message to be added
	 * @param p the Player to give the Message
	 */
	public void addMessage(Message m, Player p) {
		if(!messages.containsKey(p))
			messages.put(p, new ArrayList<Message>());
		messages.get(p).add(m);
	}
	
	/**
	 * Convenience method to add a Message to all players
	 * @param m the Message to add
	 * @param players the Players to give the Message to.
	 */
	public void addMessageToAll(Message m, List<Player> players){
		for(Player p : players)
			addMessage(m, p);
	}
	
	/**
	 * clears all current Messages. This should be called after the Messages have been processed and sent to their appropriate Players
	 */
	public void clearMessages(){
		messages.clear();
	}
}
