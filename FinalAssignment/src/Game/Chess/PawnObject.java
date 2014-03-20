package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Model.Board;

public class PawnObject extends ChessObject {
	
	private int direction;

	public PawnObject(Board board, Location location, String color, int direction) {
		super(board, location, color);
		this.direction = direction;
	}

	@Override
	public String getName() {
		return "P";
	}

	@Override
	public List<Location> getValidMoves() {
		List<Location> ans = new ArrayList<Location>();
		Location moveLoc = new Location(getLocation().x, getLocation().y+direction);
		if(isSpaceClear(moveLoc))
		{
			ans.add(moveLoc);
			if(isFirstMove()){
				Location pushLoc = new Location(moveLoc.x, moveLoc.y+direction);
				if(isSpaceClear(pushLoc))
					ans.add(pushLoc);
			}
		}
		Location attack1 = new Location(getLocation().x-1, getLocation().y+direction);
		if(isSpaceCapturable(attack1))
			ans.add(attack1);
		Location attack2 = new Location(getLocation().x+1, getLocation().y+direction);
		if(isSpaceCapturable(attack2))
			ans.add(attack2);
		return ans;
	}

}
