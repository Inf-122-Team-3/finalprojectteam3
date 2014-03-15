package View;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameGridPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer GAME_WIDTH = 600;
	public static final Integer GAME_HEIGHT = 600;
	private JButton[][] gameGrid;
	private int width;
	private int height;
	
	public GameGridPanel(String[][] board)
	{
		width = board.length;
		height = board.length > 0 ? board[0].length : 0;
		gameGrid = new JButton[width][height];
		setup(board);
	}
	
	private void setup(String[][] board)
	{
		setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		setLayout(new GridLayout(height, width));
		for(int j = 0; j < (board.length > 0 ? board[0].length : 0); j++)
		{
			for(int i = 0; i < board.length; i++)
			{
				gameGrid[i][j] = new JButton(board[i][j]);
				gameGrid[i][j].addActionListener(new TileActionListener(i,j));
				add(gameGrid[i][j]);
			}
		}
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
					gameGrid[i][j].setText(board[i][j]);
				}
			}
			return true;
		}
		return false;
	}
	
}
