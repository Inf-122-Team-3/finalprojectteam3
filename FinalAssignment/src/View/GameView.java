package View;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameView 
{
	private JFrame gameFrame;
	private JPanel gamePanel;
	public static final Integer GAME_WIDTH = 600;
	public static final Integer GAME_HEIGHT = 600;
	private JPanel scorePanel;
	public static final Integer SCORE_WIDTH = 300;
	public static final Integer SCORE_HEIGHT = 600;
	private JPanel[][] gameGrid;
	private int width;
	private int height;
	
	public GameView(String[][] board)
	{
		gameFrame = new JFrame();
		gamePanel = new JPanel();
		scorePanel = new JPanel();
		width = board.length;
		height = board.length > 0 ? board[0].length : 0;
		gameGrid = new JPanel[width][height];
		setup(board);
		//update(board);
	}
	
	private void setup(String[][] board)
	{
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		scorePanel.setPreferredSize(new Dimension(SCORE_WIDTH, SCORE_HEIGHT));
		gamePanel.setLayout(new GridLayout(width, height));
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.getContentPane().add(scorePanel, BorderLayout.LINE_START);
		gameFrame.pack();
		gameFrame.setVisible(true);

	}
	
	public boolean update(String[][] board)
	{
		int x = board.length;
		int y = board.length > 0 ? board[0].length : 0;
		if(width == x  && height == y)
		{
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < height; j++)
				{
					//add a JLabel for the text make it visible
					//add a JButton and make not visible
					//add listener for the button
				}
			}
			gameFrame.revalidate();
			gameFrame.repaint();
		}
		return false;
	}
	
	//TEST
	public void testColors()
	{
		scorePanel.setBackground(Color.YELLOW);
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				gameGrid[i][j] = new JPanel(new GridBagLayout());
				if((i%2 == 0 && j%2 == 0) || ((i+1)%2==0 && (j+1)%2==0))
					gameGrid[i][j].setBackground(Color.black);
				gamePanel.add(gameGrid[i][j]);
			}
		}
		gameFrame.revalidate();
		gameFrame.repaint();
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
				board[i][j] = i + ", " + j;
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
		hws.testColors();
	}

}
