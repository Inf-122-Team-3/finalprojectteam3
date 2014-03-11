package View;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import Model.Message;

public class GameView
{
	private JFrame gameFrame;
	private GameGridPanel gamePanel;
	private ScorePanel scorePanel;
	
	public GameView(String[][] board, Map<String, String> state, List<Message> messages) //Will be replaced w/ SimplifiedModel
	{
		gameFrame = new JFrame();
		gamePanel = new GameGridPanel(board);
		scorePanel = new ScorePanel(state);
		setup(board, state, messages);
	}
	
	private void setup(String[][] board, Map<String, String> state, List<Message> messages) //Will be replaced w/ SimplifiedModel
	{
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.getContentPane().add(scorePanel, BorderLayout.LINE_START);
		gameFrame.pack();
		gameFrame.setVisible(true);

	}
	
	public boolean update(String[][] board, Map<String, String> state, List<Message> messages) //Will be replaced w/ SimplifiedModel
	{
		if(gamePanel.update(board) && scorePanel.update(state))
		{
			gameFrame.revalidate();
			gameFrame.repaint();
			return true;
		}
		return false;
	}
	
	//TEST to be removed
	public static void main(String[] args) 
	{
		int x = 5;
		int y = 5;
		String[][] board = new String[x][y];
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y ; j++)
			{
				board[i][j] = "Hi";
			}
		}
		Map<String, String> state = new HashMap<String, String>();
		List<Message> messages = new ArrayList<Message>();
		GameView hws = new GameView(board, state, messages);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y ; j++)
			{
				board[i][j] = i + ", " + j;
			}
		}
		hws.update(board,state, messages);
	}

}