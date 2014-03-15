package Game;

import java.util.List;

import Util.Player;

public abstract class XInARowGame extends GameInstance {

	public XInARowGame(List<Player> players) {
		super(players);
	}
	/**
	 * counts the number of pieces equivalent to the piece at (x, y), in a left slanted diagonal
	 * The left slanted diagonal starts towards the top left corner, and moves toward the bottom right
	 * this is similar to a backslash ( \ )
	 * @param x the horizontal location of the piece to count [0 - width)
	 * @param y the vertical location of the piece to count [0-height)
	 * @return the number of pieces equivalent to the given piece, in a left slanted diagonal containing the piece
	 */
	public int countLeftSlantDiagonal(int x, int y){
		GameObject[][] grid = getModel().getBoard().getGrid();
		int startOffset = Math.min(x, y);
		int endOffset = Math.min(grid.length-x, (grid.length > 0 ? grid[0].length : 0)-y);
		GameObject[] checks = new GameObject[endOffset+startOffset];
		for(int i = -startOffset, j = 0; i <endOffset; i++, j++){
			checks[j] = grid[x+i][y+i];
		}
		return countSame(grid[x][y], checks);
	}
	
	/**
	 * counts the number of pieces equivalent to the piece at (x, y), in a right slanted diagonal
	 * The right slanted diagonal starts towards the top right corner, and moves toward the bottom left
	 * this is similar to a forward slash ( / )
	 * @param x the horizontal location of the piece to count [0 - width)
	 * @param y the vertical location of the piece to count [0-height)
	 * @return the number of pieces equivalent to the given piece, in a right slanted diagonal containing the piece
	 */
	public int countRightSlantDiagonal(int x, int y){
		GameObject[][] grid = getModel().getBoard().getGrid();
		int startOffset = Math.min(grid.length-x-1, y);
		int endOffset = Math.min(x+1, (grid.length > 0 ? grid[0].length : 0)-y);
		GameObject[] checks = new GameObject[endOffset+startOffset];
		for(int i = -startOffset, j=0; i <endOffset; i++, j++){
			checks[j] = grid[x-i][y+i];
		}
		return countSame(grid[x][y], checks);
	}
	
	/**
	 * counts the number of pieces equivalent to the piece at (x, y), along the same row as (x, y)
	 * @param x the horizontal location of the piece to count [0 - width)
	 * @param y the vertical location of the piece to count [0-height)
	 * @return the number of pieces equivalent to the given piece, along the same row as the piece
	 */
	public int countHorizontal(int x, int y){
		GameObject[][] grid = getModel().getBoard().getGrid();
		GameObject[] checks = new GameObject[grid.length];
		for(int i = 0; i < grid.length; i++){
			checks[i] = grid[i][y];
		}
		return countSame(grid[x][y], checks);
	}
	
	/**
	 * counts the number of pieces equivalent to the piece at (x, y), along the same column as (x, y)
	 * @param x the horizontal location of the piece to count [0 - width)
	 * @param y the vertical location of the piece to count [0-height)
	 * @return the number of pieces equivalent to the given piece, along the same column as the piece
	 */
	public int countVertical(int x, int y){
		GameObject[][] grid = getModel().getBoard().getGrid();
		GameObject[] checks = new GameObject[grid.length];
		for(int i = 0; i < (grid.length > 0 ? grid[0].length : 0); i++){
			checks[i] = grid[x][i];
		}
		return countSame(grid[x][y], checks);
	}
	
	/**
	 * checks to see if all of the given objects are equivalent. If even one of these objects is null or not equivalent, false will be returned
	 * @param initital the object to count among the given gameObjects
	 * @param gameObjects a list of Objects to check
	 * @return the maximum number of consecutive occurances of initial in gameObjects, or 0 if initial is null
	 */
	private int countSame(GameObject initial, GameObject... gameObjects){
		if(initial==null)
			return 0;
		int maxCount = 0;
		int currentCount = 0;
		for(int i = 0; i < gameObjects.length; i++){
			if(initial.equals(gameObjects[i])){
				if(++currentCount>maxCount){
					maxCount = currentCount;
				}
			}
			else{
				currentCount = 0;
			}
		}
		return maxCount;
	}

}
