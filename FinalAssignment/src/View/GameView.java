package View;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Client.Client;
import Model.Message;

public class GameView
{
	private JFrame gameFrame;
	private GameGridPanel gamePanel;
	private ScorePanel scorePanel;

	Client client;
	
	public GameView(String[][] board, Map<String, String> state, List<Message> messages, ClickListener listener, Client client) //Will be replaced w/ SimplifiedModel
	{
		gameFrame = new JFrame();
		gamePanel = new GameGridPanel(board, listener);
		scorePanel = new ScorePanel(state);
		this.client = client;
		setup(board, state, messages);
	}
	
	public GameView(String[][] board, Map<String, String> state, List<Message> messages, ClickListener listener) //Will be replaced w/ SimplifiedModel
	{
		gameFrame = new JFrame();
		gamePanel = new GameGridPanel(board, listener);
		scorePanel = new ScorePanel(state);
		setup(board, state, messages);
	}

	private void setup(String[][] board, Map<String, String> state, List<Message> messages) //Will be replaced w/ SimplifiedModel
	{
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.getContentPane().add(scorePanel, BorderLayout.LINE_START);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) { }
		SwingUtilities.updateComponentTreeUI(gameFrame);

		gameFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.out.println("Window closing");
				LobbyView lobbyView = new LobbyView(null, gameFrame);
				//LobbyView lobbyView = new LobbyView(client, gameFrame);
				//client.setLobbyView(lobbyView);
				//gameFrame.dispose();
			}
		});

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
	/*
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
		ClickListener listener = new ClickListener(){
			public void onClick(int x, int y){
				System.out.println("(" + x + "," + y +")");
			}
		};
		Map<String, String> state = new HashMap<String, String>();
		List<Message> messages = new ArrayList<Message>();
		GameView hws = new GameView(board, state, messages, listener);
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
	 */
}