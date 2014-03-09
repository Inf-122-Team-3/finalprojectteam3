package Game;

import java.util.List;

import Util.Player;

/**
 * The GameFactory interface defines objects that are responsible for creating a specific GameInstance
 * for the server to "play"
 * This GameInstance will be specific, so for instance, a TicTacToeFactory will create a TicTacToeInstance
 *
 */
public interface GameFactory {
	
	/**
	 * creates an instance of a specific game
	 * @return a specific GameInstance
	 */
	public GameInstance createGame(List<Player> players);

}
