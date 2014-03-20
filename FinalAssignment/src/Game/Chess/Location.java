package Game.Chess;

public class Location {
	
	public int x;
	public int y;
	
	public Location(){
		
	}
	
	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object other){
		if(other instanceof Location)
			return equals((Location) other);
		return false;
	}
	
	public boolean equals(Location other){
		return x==other.x&&y==other.y;
	}

}
