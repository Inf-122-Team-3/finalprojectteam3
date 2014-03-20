package View;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer SCORE_WIDTH = 300;
	public static final Integer SCORE_HEIGHT = 600;
	public JLabel scoreboard;
	public ScorePanel(Map<String, String> state)
	{
		setup(state);
	}
	
	private void setup(Map<String, String> state)
	{
		setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
		scoreboard = new JLabel();
		add(scoreboard);
	}
	
	public boolean update(Map<String, String> state)
	{
		scoreboard.setText(generateScoreboard(state));
		revalidate();
		return true;
	}
	
	private String generateScoreboard(Map<String, String> state)
	{
		if(state==null)
			return "";
		String ans = "";
		for(String s : state.keySet())
			ans = ans + s + ": " + state.get(s) + "\n";
		return ans;
	}
}

