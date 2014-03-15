package Game.Connect4;

import Game.GameObject;

public class Connect4Object implements GameObject{

	private String color;
	
	public Connect4Object(String color)
	{
		this.color = color;
	}
	
	@Override
	public String getRepresentation() {
		return color;
	}
	
	public boolean equals (Object o)
	{
		if(!(o instanceof Connect4Object))
			return false;
		return equals((Connect4Object) o);
	}
	
	public boolean equals(Connect4Object o)
	{
		return color.equals(o.color);
	}

}
