package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Model.Board;

public class BishopObject extends LinearMovingChessObject {

	public BishopObject(Board board, Location location, String color) {
		super(board, location, color);
	}

	@Override
	public String getName() {
		return "B";
	}

	@Override
	public List<Location> getValidMoves() {
		List<Location> ans = new ArrayList<Location>();
		for(int i = 1; maybeAdd(new Location(getLocation().x + i, getLocation().y + i), ans); i++)
		{}
		for(int i = 1; maybeAdd(new Location(getLocation().x - i, getLocation().y + i), ans); i++)
		{}
		for(int i = 1; maybeAdd(new Location(getLocation().x - i, getLocation().y - i), ans); i++)
		{}
		for(int i = 1; maybeAdd(new Location(getLocation().x + i, getLocation().y - i), ans); i++)
		{}
		return ans;
	}

}
