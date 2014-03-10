import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonActionListener implements ActionListener {
	
	private int xCord;
	private int yCord;
public ButtonActionListener(int x, int y) 
{
	this.xCord = x;
	this.yCord = y;
	
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	  //System.out.println("(hello)");

	System.out.println("(" + this.xCord + "," + this.yCord +")");
	  
}

}
