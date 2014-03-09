package Game;

public interface GameFactory {
	
	/**
	 * creates an instance of a specific game
	 * @return a specific GameInstance
	 */
	public GameInstance createGame();

}
