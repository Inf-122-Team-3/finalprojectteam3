package Client;

import java.util.List;

public interface IGameView {
	
	public void startGame();
	
	public void updateView();
	
	public void setGamesAvailable(List<String> v);
	
	public void receiveInvitation(String username, String game);

}
