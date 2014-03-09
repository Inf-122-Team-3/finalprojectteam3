package Game.TicTacToe;

import java.util.List;

import Game.GameFactory;
import Game.GameInstance;
import Util.Player;

/**
 * A specific GameFactory, which will create a new TicTacToeGame
 *
 */
public class TicTacToeFactory implements GameFactory{

	@Override
	public GameInstance createGame(List<Player> players) {
		return new TicTacToeInstance(players);
	}

}
