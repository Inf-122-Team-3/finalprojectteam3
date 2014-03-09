package Game.TicTacToe;

import java.util.List;

import Game.GameFactory;
import Game.GameInstance;
import Util.Player;

public class TicTacToeFactory implements GameFactory{

	@Override
	public GameInstance createGame(List<Player> players) {
		return new TicTacToeInstance(players);
	}

}
