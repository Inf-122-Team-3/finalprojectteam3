package GameViewAdaptor;

import java.util.ArrayList;
import java.util.List;

import Game.GameInstance;
import Game.GameObject;
import Model.Model;
import Util.Player;
import View.ClickListener;
import View.GameView;

public class ModelClickListener implements ClickListener {
	
	private GameInstance instance;
	private List<Player> players = new ArrayList<Player>();
	private int currentPlayer = 0;
	private GameView view;
	
	public ModelClickListener(GameInstance i, Player p1, Player p2){
		instance = i;
		players.add(p1);
		players.add(p2);
	}

	@Override
	public void onClick(int x, int y) {
		Player p = players.get(currentPlayer++);
		if(currentPlayer>=players.size())
			currentPlayer = 0;
		Model m = instance.update(x, y, p);
		String[][] grid = new String[m.getBoard().getWidth()][m.getBoard().getHeight()];
		for(int i = 0; i < m.getBoard().getWidth(); i++)
		{
			for(int j = 0; j < m.getBoard().getHeight(); j++)
			{
				GameObject o = m.getBoard().getObjectAtLocation(i, j);
				grid[i][j] = (o==null? "" : o.getRepresentation());
			}
		}
		view.update(grid, null, m.getMessages(p));
	}

	public GameView getView() {
		return view;
	}

	public void setView(GameView view) {
		this.view = view;
	}

}
