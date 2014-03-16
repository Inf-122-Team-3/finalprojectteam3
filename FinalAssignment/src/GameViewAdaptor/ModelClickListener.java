package GameViewAdaptor;

import Game.GameInstance;
import Game.GameObject;
import Model.Model;
import Util.Player;
import View.ClickListener;
import View.GameView;

public class ModelClickListener implements ClickListener {
	
	private GameInstance instance;
	private Player player;
	private GameView view;
	
	public ModelClickListener(GameInstance i, Player p){
		instance = i;
		player = p;
	}

	@Override
	public void onClick(int x, int y) {
		Model m = instance.update(x, y, player);
		String[][] grid = new String[m.getBoard().getWidth()][m.getBoard().getHeight()];
		for(int i = 0; i < m.getBoard().getHeight(); i++)
		{
			for(int j = 0; j < m.getBoard().getWidth(); j++)
			{
				GameObject o = m.getBoard().getObjectAtLocation(j, i);
				grid[i][j] = (o==null? "" : o.getRepresentation());
			}
		}
		view.update(grid, null, m.getMessages(player));
	}

	public GameView getView() {
		return view;
	}

	public void setView(GameView view) {
		this.view = view;
	}

}
