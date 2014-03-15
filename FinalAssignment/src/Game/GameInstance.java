package Game;

import java.util.List;

import Model.Message;
import Model.Model;
import Util.Player;

/**
 * A specific instance of a game. This class will be in charge of constructing an appropriate model,
 * and performing any logic necessary for it's game to be run
 *
 */
public abstract class GameInstance {
	
	private Model model;
	private List<Player> players;
	private boolean gameOver = false;
	
	/**
	 * Constructs a new GameInstance, being played by players
	 * @param players the Player objects currently playing this game
	 */
	public GameInstance(List<Player> players){
		this.players = players;
		model = createModel();
	}
	
	/**
	 * called by the server whenever a player clicks on a grid location
	 * @param x the horizontal location on the grid [0-width)
	 * @param y the vertical location on the grid [0-height)
	 * @param p the player that clicked
	 * @return the updated model
	 */
	public Model update(int x, int y, Player p){
		onPlayerClick(x, y, p);
		return model;
	}
	
	public Model getModel() {
		return model;
	}
	
	/**
	 * Sends a message to the given Player, informing him that he has won the Match.
	 * Also increments his number of wins + 1
	 * @param p the Player to alert
	 */
	public void alertWin(Player p){
		getModel().addMessage(new Message("you won!"), p);
		p.setWins(p.getWins()+1);
	}
	
	/**
	 * Sends a message to the given Player, informing him that he has lost the Match.
	 * Also increments his number of losses + 1
	 * @param p the Player to alert
	 */
	public void alertLoss(Player p){
		getModel().addMessage(new Message("you lost!"), p);
		p.setLosses(p.getLosses()+1);
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	public void setGameOver(){
		gameOver = true;
	}
	
	/**
	 * Called when the GameInstance is created
	 * this hook method is here in order to try and guarantee that a model gets created.
	 * @return this Game's model
	 */
	protected abstract Model createModel();
	
	/**
	 * hook method called when the Player p clicks on a grid location
	 * @param x the horizontal location on the grid [0-width)
	 * @param y the vertical location on the grid [0-height)
	 * @param p the player that clicked
	 */
	public abstract void onPlayerClick(int x, int y, Player p);
	
	/**
	 * called by the server when a player leaves
	 * @param p the player that left
	 */
	public abstract void onPlayerLeave(Player p);

	public List<Player> getPlayers() {
		return players;
	}

}
