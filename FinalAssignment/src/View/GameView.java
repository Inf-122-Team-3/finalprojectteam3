package View;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

public class GameView
{
	private JFrame gameFrame;
	private GameGridPanel gamePanel;
	private ScorePanel scorePanel;
	
	public GameView(String[][] board)//Should actually take SimplfiedModel
	{
		gameFrame = new JFrame();
		gamePanel = new GameGridPanel(board);
		scorePanel = new ScorePanel();
		setup(board);
	}
	
	private void setup(String[][] board)
	{
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.getContentPane().add(scorePanel, BorderLayout.LINE_START);
		gameFrame.pack();
		gameFrame.setVisible(true);

	}
	
	public boolean update(String[][] board)
	{
		if(gamePanel.update(board))
		{
			gameFrame.revalidate();
			gameFrame.repaint();
			return true;
		}
		return false;
	}
	
	//TEST
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
		GameView hws = new GameView(board);
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
		hws.update(board);
	}

}



