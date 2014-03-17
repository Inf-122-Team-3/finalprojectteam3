package View;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.Client;

public class LobbyView {
	private JFrame lobbyFrame;
	private JPanel lobbyPanel;
	private JComboBox<String> playerList;
	private JLabel playersLabel;
	private JComboBox<String> gameList;
	private JLabel gamesLabel;
	//private JButton refresh;
	private JButton requestGame;
	private JButton freshLobby;
	
	private Client client;
	private JFrame previousFrame;
	
	public LobbyView(String[] players, String[] games, Client client, JFrame previousFrame) 
	{
		lobbyFrame = new JFrame();
		lobbyPanel = new JPanel();
		playerList = new JComboBox<String>(players);
		gameList = new JComboBox<String>(games);
		playersLabel = new JLabel("Online players:");
		gamesLabel = new JLabel("Choose a game to play");
		//refresh = new JButton("Refresh");
		requestGame = new JButton("Invite player");
		freshLobby = new JButton("Refresh");
		this.previousFrame = previousFrame;
		this.client = client;
		
		requestGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestGame_actionPerformed(e);
			}
		});
		
		freshLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshPlayers_actionPerformed(e);
			}
		});
		
		setup(players, games);
	}
	
	public LobbyView(Client client, JFrame previousFrame)
	{
		this(new String[0], new String[0], client, previousFrame);
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
		lobbyPanel.add(freshLobby);
		lobbyPanel.validate();
		lobbyFrame.add(lobbyPanel);
		lobbyFrame.setBounds(previousFrame.getBounds());
		
		lobbyFrame.pack();
		lobbyFrame.setVisible(true);

	}
	
	private void requestGame_actionPerformed(ActionEvent e) {
		String game = (String) gameList.getSelectedItem();
		String username = (String) playerList.getSelectedItem();
	}
	
	private void refreshPlayers_actionPerformed(ActionEvent e)
	{
		client.refreshListOfPlayers();
		JOptionPane.showMessageDialog(lobbyFrame, "Refreshed!");
	}
	
	public void updateListOfAvaliablePlayers(String[] listOfPlayers) {
		playerList.removeAll();
		for(String s : listOfPlayers) {
			playerList.addItem(s);
		}
	}
	
	public void updateListOfGames(String[] listOfGames) {
		gameList.removeAllItems();
		for(String s : listOfGames) {
			gameList.addItem(s);
		}
	}
	
	public boolean update()
	{
		return false;
	}
}
