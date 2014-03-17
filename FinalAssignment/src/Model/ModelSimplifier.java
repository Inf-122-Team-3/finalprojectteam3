package Model;

import java.util.ArrayList;

import Util.Player;
import Util.SimplifiedModel;

public class ModelSimplifier {
	
	public static SimplifiedModel simplify(Model m, Player p){
		SimplifiedModel sm = new SimplifiedModel();
		sm.grid = new String[m.getBoard().getWidth()][m.getBoard().getHeight()];
		for(int i = 0; i < m.getBoard().getWidth(); i++)
		{
			for(int j = 0; j < m.getBoard().getHeight(); j++)
			{
				if(m.getBoard().getObjectAtLocation(i, j) != null)
					sm.grid[i][j] = m.getBoard().getObjectAtLocation(i, j).getRepresentation();
			}
		}
		
		sm.messages = new ArrayList<Message>();
		if(p != null) {
			if(m.getMessages(p) != null) {
				for(Message mess : m.getMessages(p))
					sm.messages.add(mess);
			}
		}
		
		return sm;
	}
}
