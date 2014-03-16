package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class RightLobbyViewPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer RightLobbyView_WIDTH = 400;
	public static final Integer RightLobbyView_Height  = 400;

	//manage displaying of rooms available
	public RightLobbyViewPanel()
	{
		setup();
	}
	
	private void setup()
	{
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(RightLobbyView_WIDTH, RightLobbyView_Height));
	}
	
	public boolean update()
	{
		//TODO: Write the update!
		return false;
	}
	
}
