package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Model.Board;

public class RookObject extends LinearMovingChessObject {

	public RookObject(Board board, Location location, String color) {
		super(board, location, color);
	}

	@Override
	public String getName() {
		return "R";
	}

	@Override
	public List<Location> getValidMoves() {
		List<Location> ans = new ArrayList<Location>();
		for(int i = getLocation().x+1; maybeAdd(new Location(i, getLocation().y), ans); i++)
		{}
		for(int i = getLocation().x-1; maybeAdd(new Location(i, getLocation().y), ans); i--)
		{}
		for(int i = getLocation().y+1; maybeAdd(new Location(getLocation().x, i), ans); i++)
		{}
		for(int i = getLocation().y-1; maybeAdd(new Location(getLocation().x, i), ans); i--)
		{}
		return ans;
	}
	
	

}
