package View;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer SCORE_WIDTH = 300;
	public static final Integer SCORE_HEIGHT = 600;
	
	public ScorePanel(Map<String, String> state)
	{
		setup(state);
	}
	
	private void setup(Map<String, String> state)
	{
		setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
	}
	
	public boolean update(Map<String, String> state)
	{
		//TODO: Write the update!
		return false;
	}
	
	
	
}

