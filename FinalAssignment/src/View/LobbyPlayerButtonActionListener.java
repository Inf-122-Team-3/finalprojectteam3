package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LobbyPlayerButtonActionListener implements ActionListener {
	
	private String PlayerName;
	
public LobbyPlayerButtonActionListener(String playerName) 
{
	this.PlayerName = playerName;
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	  //System.out.println("(hello)");

	System.out.println("(" + this.PlayerName +"Button Clicked" + ")");
	  
}

}
