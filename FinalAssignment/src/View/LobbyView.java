package View;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class LobbyView {
	private JFrame lobbyFrame;
	private JPanel lobbyPanel;
	private JComboBox<String> playerList;
	private JLabel playersLabel;
	private JComboBox<String> gameList;
	private JLabel gamesLabel;
	//private JButton refresh;
	private JButton requestGame;
	
	public LobbyView(String[] players, String[] games) 
	{
		lobbyFrame = new JFrame();
		lobbyPanel = new JPanel();
		playerList = new JComboBox<String>(players);
		gameList = new JComboBox<String>(games);
		playersLabel = new JLabel("Online players:");
		gamesLabel = new JLabel("Choose a game to play");
		//refresh = new JButton("Refresh");
		requestGame = new JButton("Invite player");
		setup(players, games);
	}
	
	public LobbyView()
	{
		this(new String[0], new String[0]);
	}
	
	private void setup(String[] players, String[] games) 
	{
		lobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lobbyFrame.setPreferredSize(new Dimension(300, 200));
		lobbyPanel.setLayout(new BoxLayout(lobbyPanel, BoxLayout.Y_AXIS));
		//lobbyPanel.add(refresh);
		lobbyPanel.add(playersLabel);
		lobbyPanel.add(playerList);
		lobbyPanel.add(gamesLabel);
		lobbyPanel.add(gameList);
		lobbyPanel.add(requestGame);
		lobbyPanel.validate();
		lobbyFrame.add(lobbyPanel);
		
		lobbyFrame.pack();
		lobbyFrame.setVisible(true);

	}
	
	public boolean update()
	{
		return false;
	}
	
	//TEST to be removed
	public static void main(String[] args) 
	{
		String[] games = new String[6];
		games[0] = "Game1";
		games[1] = "Game2";
		games[2] = "Game3";
		games[3] = "Game4";
		games[4] = "Game5";
		games[5] = "Game6";
		
	 	String[] players = new String[6];
	 	players[0] = "Billy";
	 	players[1] = "Sally";
	 	players[2] = "Bobly";
	 	players[3] = "Sely";
	 	players[4] = "Lilly";
	 	players[5] = "Rily";
	 	
	 	LobbyView lv = new LobbyView(players, games);
	}
}
