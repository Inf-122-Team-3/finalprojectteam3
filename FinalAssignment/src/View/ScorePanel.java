package View;

import java.awt.Dimension;

import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer SCORE_WIDTH = 300;
	public static final Integer SCORE_HEIGHT = 600;
	
	public ScorePanel()
	{
		setup();
	}
	
	private void setup()
	{
		setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
	}
	
	
	
}

