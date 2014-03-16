package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Model.Board;

public class KingObject extends ChessObject {

	public KingObject(Board board, Location location, String color) {
		super(board, location, color);
	}

	@Override
	public String getName() {
		return "K";
	}

	@Override
	public List<Location> getValidMoves() {
		List<Location> ans = new ArrayList<Location>();
		quickerTryAdd(1, 1, ans);
		quickerTryAdd(-1, 1, ans);
		quickerTryAdd(-1, -1, ans);
		quickerTryAdd(1, -1, ans);
		quickerTryAdd(1, 0, ans);
		quickerTryAdd(-1, 0, ans);
		quickerTryAdd(0, 1, ans);
		quickerTryAdd(0, -1, ans);
		return ans;
	}
	
	public boolean isLocationThreatened(int x, int y){
		return isLocationThreatened(new Location(x, y));
	}
	
	public boolean isLocationThreatened(Location loc){
		for(int i = 0; i < getBoard().getWidth(); i++)
		{
			for(int j = 0; j < getBoard().getHeight(); j++)
			{
				if(getBoard().getGrid()[i][j]!=null&&isPieceThreatening((ChessObject)getBoard().getGrid()[i][j], loc))
					return true;
			}
		}
		return false;
	}
	
	public boolean isThreatened(){
		return isLocationThreatened(getLocation());
	}
	
	public boolean isPieceThreatening(ChessObject piece, Location loc)
	{
		if(isSameTeam(piece))
			return false;
		List<Location> moves = piece.getValidMoves();
		if(moves.contains(loc))
			return true;
		return false;
	}
	
	private void quickTryAdd(Location loc, List<Location> validLocs){
		if((isSpaceClear(loc)||isSpaceCapturable(loc))&&!isLocationThreatened(loc))
			validLocs.add(loc);
	}
	
	private void quickerTryAdd(int x, int y, List<Location> validLocs){
		quickTryAdd(new Location(getLocation().x+x, getLocation().y + y), validLocs);
	}

}
