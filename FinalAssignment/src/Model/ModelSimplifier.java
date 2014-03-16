package Model;

import java.util.ArrayList;

import Util.Player;
import Util.SimplifiedModel;

public class ModelSimplifier {
	
	public SimplifiedModel simplify(Model m, Player p){
		SimplifiedModel sm = new SimplifiedModel();
		sm.grid = new String[m.getBoard().getWidth()][m.getBoard().getHeight()];
		for(int i = 0; i < m.getBoard().getWidth(); i++)
		{
			for(int j = 0; j < m.getBoard().getHeight(); j++)
			{
				sm.grid[i][j] = m.getBoard().getObjectAtLocation(i, j).getRepresentation();
			}
		}
		sm.messages = new ArrayList<Message>();
		for(Message mess : m.getMessages(p))
			sm.messages.add(mess);
		return sm;
	}

}
