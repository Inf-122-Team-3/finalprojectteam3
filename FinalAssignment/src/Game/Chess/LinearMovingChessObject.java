package Game.Chess;

import java.util.List;

import Model.Board;

public abstract class LinearMovingChessObject extends ChessObject {

	public LinearMovingChessObject(Board board, Location location, String color) {
		super(board, location, color);
	}
	
	/**
	 * tries to add the location to the list of valid locations, if that location is empty or occupied by a chess piece
	 * @param loc the location to check
	 * @param validLocs the list to add the location to, maybe
	 * @return whether or not the loop should continue
	 */
	public boolean maybeAdd(Location loc, List<Location> validLocs)
	{
		if(isSpaceClear(loc))
			validLocs.add(loc);
		else
		{
			if(isSpaceCapturable(loc))
				validLocs.add(loc);
			return false;
		}
		return true;
	}

}
