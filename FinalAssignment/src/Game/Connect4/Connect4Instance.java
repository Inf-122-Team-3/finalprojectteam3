package Game.Connect4;

import java.util.List;

import Game.GameObject;
import Game.XInARowGame;
import Model.Board;
import Model.Message;
import Model.Model;
import Util.Player;

public class Connect4Instance extends XInARowGame{

	private int turn;
	
	public Connect4Instance(List<Player> players) {
		super(players);
	}

	@Override
	protected Model createModel() {
		return new Model(new Board(7,6));
	}

	/**
	 * 1. Checks to see if it is the player's turn, sends message and does nothing to the model if it isn't.
	 * 2. Checks to see if the column player selected is already filled to the top. Tells player to pick a 
	 * 		different column if true. 
	 * 3. Starting from the bottom of the selected column and iterating up, find the next empty cell in that column.
	 * 4. When player's color piece has been 'dropped' into the selected column, check if move creates 4 in a row.
	 * 		If four in a row, alert current player they won, alert everyone else that they lost, and set game to game over.
	 * 		Else, change turn to next player's turn.
	 */
	@Override
	public void onPlayerClick(int x, int y, Player p) {
		Model m = getModel();
		m.clearMessages();
		if (isPlayersTurn(p))
		{
			if(m.getBoard().getObjectAtLocation(x, 0)==null)
			{
				for(int i = 5; i >= 0; i--)
				{
					GameObject o = m.getBoard().getObjectAtLocation(x, i);
					if (o==null)
					{
						m.getBoard().setObjectAtLocation(x, i, new Connect4Object(turn==0 ? "R" : "Y"));
						if(checkFourInARow(x, i))
						{
							alertWin(p);
							for(int j=0; j<getPlayers().size(); j++)
								if(!getPlayers().get(j).equals(p))
									alertLoss(getPlayers().get(j));
							setGameOver();
						}
						else if(++turn>=getPlayers().size())
							turn = 0;
						i = -1; //break out of the loop
					}
				}
			}
			else
				getModel().addMessage(new Message("Column selected is full! Please try again."), p);
		}
		else
		{
			if (isGameOver())
				getModel().addMessage(new Message("The game is over!"), p);
			else
				getModel().addMessage(new Message("It's not your turn"), p);
		}
		
	}

	/**
	 * checks to see if there are four similar pieces in a row, centered around the given location
	 * @param x the horizontal location on the grid [0-width)
	 * @param y the vertical location on the grid [0-height)
	 * @return whether or not there exists a four in a row around this location
	 */
	private boolean checkFourInARow(int x, int y) {
		return countLeftSlantDiagonal(x,y)==4 || countRightSlantDiagonal(x,y)==4|| countHorizontal(x,y)==4 || countVertical(x,y)==4;
	}

	/**
	 * checks to see if it is the given Player's turn
	 * @param p: the Player to check against
	 * @return whether or not it is that Player's turn
	 */
	private boolean isPlayersTurn(Player p) {
		return !isGameOver()&&p.equals(getPlayers().get(turn));
	}

	/**
	 * Removes the given player from the game and alert them that they lost. 
	 * If there is a remaining player, alert them that they won.
	 * @param p: the Player that left the game
	 */
	@Override
	public void onPlayerLeave(Player p) {
		getPlayers().remove(p);
		if(getPlayers().size()==0 || isGameOver())
			return;
		alertLoss(p);
		if(getPlayers().size()==1)
			alertWin(getPlayers().get(0));
		
	}

}
