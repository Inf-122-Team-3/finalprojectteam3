package Game.Chess;

import java.util.List;

import Util.Player;
import Game.GameFactory;
import Game.GameInstance;

public class ChessFactory implements GameFactory {

	@Override
	public GameInstance createGame(List<Player> players) {
		return new ChessInstance(players);
	}

}
