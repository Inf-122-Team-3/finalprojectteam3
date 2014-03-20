package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Model.Board;

public class QueenObject extends LinearMovingChessObject {

	public QueenObject(Board board, Location location, String color) {
		super(board, location, color);
	}

	@Override
	public String getName() {
		return "Q";
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
