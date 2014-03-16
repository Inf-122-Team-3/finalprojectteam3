package View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LobbyRoomButtonActionListener implements ActionListener {
	
	private String RoomName;
	
public LobbyRoomButtonActionListener(String playerName) 
{
	this.RoomName = playerName;
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	  //System.out.println("(hello)");

	System.out.println("(" + this.RoomName +"Button Clicked" + ")");
	  
}

}
