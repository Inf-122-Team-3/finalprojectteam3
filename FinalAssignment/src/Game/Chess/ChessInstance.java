package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Game.GameInstance;
import Model.Board;
import Model.Model;
import Util.Player;

public class ChessInstance extends GameInstance{
	
	private List<KingObject> kings;

	public ChessInstance(List<Player> players) {
		super(players);
	}

	@Override
	protected Model createModel() {
		Model m = new Model(new Board(8, 8));
		kings = new ArrayList<KingObject>();
		for(int i = 0; i < m.getBoard().getWidth(); i++)
		{
			
		}
		return m;
	}

	@Override
	public void onPlayerClick(int x, int y, Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerLeave(Player p) {
		// TODO Auto-generated method stub
		
	}

}
