package Model;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	private Board board;
	private List<Message> messages;
	
	public Model(){
		
	}
	
	public Model(Board b){
		board = b;
		messages = new ArrayList<Message>();
	}

	public Board getBoard() {
		return board;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void addMessage(Message m) {
		messages.add(m);
	}
	
	public void clearMessages(){
		messages.clear();
	}
}
