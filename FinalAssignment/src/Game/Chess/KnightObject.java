package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Model.Board;

public class KnightObject extends ChessObject {

	public KnightObject(Board board, Location location, String color) {
		super(board, location, color);
	}

	@Override
	public String getName() {
		return "H";
	}

	@Override
	public List<Location> getValidMoves() {
		List<Location> ans = new ArrayList<Location>();
		quickerTryAdd(2, 1, ans);
		quickerTryAdd(-2, -1, ans);
		quickerTryAdd(-2, 1, ans);
		quickerTryAdd(2, -1, ans);
		quickerTryAdd(1, 2, ans);
		quickerTryAdd(-1, -2, ans);
		quickerTryAdd(-1, 2, ans);
		quickerTryAdd(1, -2, ans);
		return ans;
	}
	
	private void quickTryAdd(Location loc, List<Location> validLocs){
		if(isSpaceClear(loc)||isSpaceCapturable(loc))
			validLocs.add(loc);
	}
	
	private void quickerTryAdd(int x, int y, List<Location> validLocs){
		quickTryAdd(new Location(getLocation().x+x, getLocation().y + y), validLocs);
	}

}
