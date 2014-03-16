package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileActionListener implements ActionListener
{
	private int xCoord;
	private int yCoord;
	private ClickListener listener;
	public TileActionListener(int x, int y, ClickListener listener)
	{
		xCoord = x;
		yCoord = y;
		this.listener = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		  //System.out.println("(hello)");

		listener.onClick(xCoord, yCoord);
		  
	}
}