package Game.TicTacToe;

import Game.GameObject;

public class TicTacToeObject implements GameObject {
	
	private String character;
	
	public TicTacToeObject(String character){
		this.character = character;
	}

	@Override
	public String getRepresentation() {
		return character;
	}
	
	public boolean equals(Object o){
		if(!(o instanceof TicTacToeObject))
			return false;
		return equals((TicTacToeObject) o);
	}
	
	public boolean equals(TicTacToeObject o){
		return character.equals(o.character);
	}

}
