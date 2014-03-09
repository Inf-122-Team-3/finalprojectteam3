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
					alertWin(p);
					for(int i = 0; i < getPlayers().size(); i++)
						if(!getPlayers().get(i).equals(p))
							alertLoss(getPlayers().get(i));
					setGameOver();
				}
				else if(++turn>=getPlayers().size())
					turn = 0;
			}
			else
				getModel().addMessage(new Message("there is already a piece there!"), p);
		}
		else
		{
			if(isGameOver())
				getModel().addMessage(new Message("the game is over!"), p);
			else
				getModel().addMessage(new Message("it's not your turn"), p);
		}
	}
	
	/**
	 * checks to see if it is the given Player's turn
	 * @param p the Player to check against
	 * @return whether or not it is that Player's turn
	 */
	private boolean isPlayersTurn(Player p){
		return !isGameOver()&&p.equals(getPlayers().get(turn));
	}
	
	/**
	 * checks to see if there are three similar pieces in a row, centered around the given location
	 * @param x the horizontal location on the grid [0-width)
	 * @param y the vertical location on the grid [0-height)
	 * @return whether or not there exists a three in a row around this location
	 */
	private boolean checkThreeInARow(int x, int y){
		return checkDiagonals()||checkHorizontal(y)||checkVertical(x);
	}
	
	/**
	 * checks the diagonals for three in a row. Since there are only two possible diagonals, this check is the same regardless of inputs
	 * @return whether or not a 3 in a row exists.
	 */
	private boolean checkDiagonals(){
		GameObject[][] grid = getModel().getBoard().getGrid();
		return isSame(grid[0][0], grid[1][1], grid[2][2])||isSame(grid[2][0], grid[1][1], grid[0][2]);
	}
	
	/**
	 * checks if a three in a row exists along the given row
	 * @param y the vertical location on the grid to check [0-height)
	 * @return whether or not a 3 in a row exists
	 */
	private boolean checkHorizontal(int y){
		GameObject[][] grid = getModel().getBoard().getGrid();
		return isSame(grid[0][y], grid[1][y], grid[2][y]);
	}
	
	/**
	 * checks if a three in a row exists along the given column
	 * @param x the horizontal location on the grid to check [0-width)
	 * @return whether or not a 3 in a row exists
	 */
	private boolean checkVertical(int x){
		GameObject[][] grid = getModel().getBoard().getGrid();
		return isSame(grid[x][0], grid[x][1], grid[x][2]);
	}
	
	/**
	 * checks to see if all of the given objects are equivalent. If even one of these objects is null or not equivalent, false will be returned
	 * @param gameObjects a list of Objects to check
	 * @return whether or not all the given objects are equivalent
	 */
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
	
	/**
	 * Sends a message to the given Player, informing him that he has won the Match.
	 * Also increments his number of wins + 1
	 * @param p the Player to alert
	 */
	private void alertWin(Player p){
		getModel().addMessage(new Message("you won!"), p);
		p.setWins(p.getWins()+1);
	}
	
	/**
	 * Sends a message to the given Player, informing him that he has lost the Match.
	 * Also increments his number of losses + 1
	 * @param p the Player to alert
	 */
	private void alertLoss(Player p){
		getModel().addMessage(new Message("you lost!"), p);
		p.setLosses(p.getLosses()+1);
	}
	
	private boolean isGameOver(){
		return turn<0;
	}
	
	private void setGameOver(){
		turn = -1;
	}

	@Override
	public void onPlayerLeave(Player p) {
		getPlayers().remove(p);
		if(getPlayers().size()==0||isGameOver())
			return;
		alertLoss(p);
		if(getPlayers().size()==1)
			alertWin(getPlayers().get(0));
	}

}
