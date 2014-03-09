package Model;

import Game.GameObject;

/**
 * The Board part of the Simulation. Simply a N x M sized array of GameObjects
 *
 */
public class Board {
	
	private GameObject[][] grid;
	
	public Board(int width, int height){
		grid = new GameObject[width][height];
	}

	public GameObject[][] getGrid() {
		return grid;
	}
	
	/**
	 * convenience method for getting the GameObject at location (x, y)
	 * @param x the horizontal location on the grid [0-width)
	 * @param y the vertical location on the grid [0-height)
	 * @return the object at that location, or null if there is no object there
	 */
	public GameObject getObjectAtLocation(int x, int y){
		return grid[x][y];
	}
	
	/**
	 * convenience method for setting the GameObject at location (x, y)
	 * @param x the horizontal location on the grid [0-width)
	 * @param y the vertical location on the grid [0-height)
	 * @param o the object to be added at that location, or null if the object at that location should be removed
	 */
	public void setObjectAtLocation(int x, int y, GameObject o){
		grid[x][y] = o;
	}
	
	public int getWidth(){
		return grid.length;
	}
	
	public int getHeight(){
		return grid[0].length;
	}

}
