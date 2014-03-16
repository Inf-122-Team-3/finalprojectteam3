package View;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JPanel;

public class ListOfPlayersPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer ListOfPlayersPanel_WIDTH = 300;
	public static final Integer ListOfPlayersPanel_HEIGHT = 600;
	
	public ListOfPlayersPanel(Map<String, String> state)
	{
		setup(state);
	}
	
	private void setup(Map<String, String> state)
	{
		setPreferredSize(new Dimension(ListOfPlayersPanel_WIDTH, ListOfPlayersPanel_HEIGHT));
	}
	
	public boolean update(Map<String, String> state)
	{
		//TODO: Write the update!
		return false;
	}
	
	
	
}

