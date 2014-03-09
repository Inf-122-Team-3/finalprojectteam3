package Game;

import java.util.List;

import Util.Player;

public interface GameFactory {
	
	/**
	 * creates an instance of a specific game
	 * @return a specific GameInstance
	 */
	public GameInstance createGame(List<Player> players);

}
