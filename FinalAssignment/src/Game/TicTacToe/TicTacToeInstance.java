package Game.TicTacToe;

import java.util.List;

import Game.GameInstance;
import Game.GameObject;
import Model.Board;
import Model.Message;
import Model.Model;
import Util.Player;

/**
 * An instance of TicTacToe, this class will be responsible for checking the Rules of TicTacToe,
 * and updating the model accordingly.
 *
 */
public class TicTacToeInstance extends GameInstance{
	
	private int turn;
	
	public TicTacToeInstance(List<Player> players){
		super(players);
	}

	@Override
	protected Model createModel() {
		return new Model(new Board(3, 3));
	}

	@Override
	public void onPlayerClick(int x, int y, Player p) {
		Model m = getModel();
		if(isPlayersTurn(p))
		{
			GameObject o = m.getBoard().getObjectAtLocation(x, y);
			if(o==null){
				m.getBoard().setObjectAtLocation(x, y, new TicTacToeObject(turn==0 ? "X" : "O"));
				if(checkThreeInARow(x, y)){
					m.addMessage(new Message("you won!"), p);
					p.setWins(p.getWins()+1);
					for(int i = 0; i < getPlayers().size(); i++)
						if(i!=turn)
						{
							m.addMessage(new Message("you lost!"), getPlayers().get(i));
							getPlayers().get(i).setLosses(getPlayers().get(i).getLosses()+1);
						}
					turn = -1;
				}
				if(++turn>=getPlayers().size())
					turn = 0;
			}
		}
		else
		{
			getModel().addMessage(new Message("it's not you're turn"), p);
		}
	}
	
	private boolean isPlayersTurn(Player p){
		return turn>=0&&turn<getPlayers().size()&&p.equals(getPlayers().get(turn));
	}
	
	private boolean checkThreeInARow(int x, int y){
		return checkDiagonals()||checkHorizontal(y)||checkVertical(x);
	}
	
	private boolean checkDiagonals(){
		GameObject[][] grid = getModel().getBoard().getGrid();
		return isSame(grid[0][0], grid[1][1], grid[2][2])||isSame(grid[2][0], grid[1][1], grid[0][2]);
	}
	
	private boolean checkHorizontal(int y){
		GameObject[][] grid = getModel().getBoard().getGrid();
		return isSame(grid[0][y], grid[1][y], grid[2][y]);
	}
	
	private boolean checkVertical(int x){
		GameObject[][] grid = getModel().getBoard().getGrid();
		return isSame(grid[x][0], grid[x][1], grid[x][2]);
	}
	
	private boolean isSame(GameObject... gameObjects){
		GameObject o = gameObjects[0];
		if(o==null)
			return false;
		for(int i = 1; i < gameObjects.length; i++){
			if(gameObjects[i]==null||!gameObjects[i].equals(o))
				return false;
		}
		return true;
	}

	@Override
	public void onPlayerLeave(Player p) {
		// TODO Auto-generated method stub
		
	}

}
