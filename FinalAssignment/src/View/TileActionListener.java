package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileActionListener implements ActionListener
{
	private int xCoord;
	private int yCoord;
	public TileActionListener(int x, int y)
	{
		xCoord = x;
		yCoord = y;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		  //System.out.println("(hello)");

		System.out.println("(" + xCoord + "," + yCoord +")");
		  
	}
}