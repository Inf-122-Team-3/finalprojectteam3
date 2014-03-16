package Game.Gomoku;

import java.util.List;

import Game.GameFactory;
import Game.GameInstance;
import Util.Player;

public class GomokuFactory implements GameFactory{
	@Override
	public GameInstance createGame(List<Player> players) {
		return new GomokuInstance(players);
	}
}
