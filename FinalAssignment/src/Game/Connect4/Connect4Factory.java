package Game.Connect4;

import java.util.List;

import Util.Player;
import Game.GameFactory;
import Game.GameInstance;

/**
 * A specific GameFactory, which will create a new TicTacToeGame
 *
 */
public class Connect4Factory implements GameFactory {

	@Override
	public GameInstance createGame(List<Player> players) {
		return new Connect4Instance(players);
	}

}
