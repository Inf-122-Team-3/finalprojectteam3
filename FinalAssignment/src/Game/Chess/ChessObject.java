package Game.Chess;

import java.util.List;

import Game.GameObject;
import Model.Board;

public abstract class ChessObject implements GameObject {
	
	private Board board;
	private Location location;
	private String color;
	private boolean selected;
	private boolean firstMove = true;
	
	public ChessObject(Board board, Location location, String color){
		this.setBoard(board);
		this.location = location;
		this.color = color;
	}
	
	@Override
	public String getRepresentation() {
		return (isSelected() ? "-" + getColor() + getName() + "-" : " " + getColor() + getName() + " ");
	}
	
	public abstract String getName();
	
	public abstract List<Location> getValidMoves();

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Location getLocation() {
		return location;
	}

	public void move(Location nextLocation){
		board.setObjectAtLocation(nextLocation.x, nextLocation.y, this);
		board.setObjectAtLocation(location.x, location.y, null);
		location.set(nextLocation.x, nextLocation.y);
		firstMove = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public boolean isSpaceClear(int x, int y){
		return isInBoard(x, y)&&board.getGrid()[x][y]==null;
	}
	
	public boolean isSpaceClear(Location loc){
		return isSpaceClear(loc.x, loc.y);
	}
	
	public boolean isSpaceOccupied(int x, int y){
		return isInBoard(x, y)&&board.getGrid()[x][y]!=null;
	}
	
	public boolean isSpaceOccupied(Location loc){
		return isSpaceOccupied(loc.x, loc.y);
	}
	
	public boolean isInBoard(int x, int y){
		return x>=0&&x<board.getWidth()&&y>=0&&y<board.getHeight();
	}
	
	public boolean isSameTeam(ChessObject other){
		return other.color.equals(color);
	}
	
	public boolean isSpaceCapturable(int x, int y){
		return isInBoard(x, y)&&board.getGrid()[x][y]!=null&&!isSameTeam((ChessObject)board.getGrid()[x][y]);
	}
	
	public boolean isSpaceCapturable(Location loc){
		return isSpaceCapturable(loc.x, loc.y);
	}

	public boolean isFirstMove() {
		return firstMove;
	}
	
}
