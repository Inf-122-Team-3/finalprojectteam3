package Game.Gomoku;

import java.util.List;

import Game.GameObject;
import Game.XInARowGame;
import Game.TicTacToe.TicTacToeObject;
import Model.Board;
import Model.Message;
import Model.Model;
import Util.Player;

public class GomokuInstance extends XInARowGame{
	private int turn;
	
	public GomokuInstance(List<Player> players){
		super(players);
	}

	@Override
	protected Model createModel() {
		return new Model(new Board(19, 19));
	}

	@Override
	public void onPlayerClick(int x, int y, Player p) {
		Model m = getModel();
		m.clearMessages();
		if(isPlayersTurn(p))
		{
			GameObject o = m.getBoard().getObjectAtLocation(x, y);
			if(o==null){
				m.getBoard().setObjectAtLocation(x, y, new GomokuObject(turn == 0 ? "B" : "W"));
				if(checkFiveInARow(x, y)){
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
	private boolean checkFiveInARow(int x, int y){
		return countLeftSlantDiagonal(x,y)==5||countRightSlantDiagonal(x,y)==5||countHorizontal(x,y)==5||countVertical(x,y)==5;
	}

	
	/**
	 * Removes the given player from the game and alert them that they lost. 
	 * If there is a remaining player, alert them that they won.
	 * @param p: the Player that left the game
	 */
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
