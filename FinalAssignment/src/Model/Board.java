package Model;

import Game.GameObject;

public class Board {
	
	private GameObject[][] grid;
	
	public Board(int width, int height){
		grid = new GameObject[width][height];
	}

	public GameObject[][] getGrid() {
		return grid;
	}
	
	public GameObject getObjectAtLocation(int x, int y){
		return grid[x][y];
	}
	
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
