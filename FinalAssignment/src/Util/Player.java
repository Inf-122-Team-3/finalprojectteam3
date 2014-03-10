package Util;

public class Player {
	
	private String username;
	private int id;
	private int wins;
	private int losses;
	
	public Player(int id){
		this.id = id;
	}
	
	public Player(int id, String username){
		this.id = id;
		setUsername(username);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Player))
			return false;
		return equals((Player)o);
	}
	
	public boolean equals(Player p){
		return id==p.id&&username.equals(p.username);
	}

}
