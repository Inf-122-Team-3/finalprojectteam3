package Game.Gomoku;

import Game.GameObject;


public class GomokuObject implements GameObject{
	private String color;
	
	public GomokuObject(String color)
	{
		this.color = color;
	}
	
	@Override
	public String getRepresentation() {
		return color;
	}
	
	public boolean equals (Object o)
	{
		if(!(o instanceof GomokuObject))
			return false;
		return equals((GomokuObject) o);
	}
	
	public boolean equals(GomokuObject o)
	{
		return color.equals(o.color);
	}
}
