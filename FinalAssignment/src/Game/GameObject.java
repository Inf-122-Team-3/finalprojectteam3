package Game;

/**
 * A generic Object in the Game, this should be implemented by whatever number of classes necessary
 * to run a specific game.
 * Additionally, this object has to have some way to be displayed by string
 *
 */
public interface GameObject {
	
	/**
	 * Every game object needs some sort of simple string representation
	 * @return a string representation of this GameObject
	 */
	public String getRepresentation();

}
