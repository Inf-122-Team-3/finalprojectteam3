package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Model.Message;

public class LobbyView {
	private JFrame LobbyFrame;
	private RightLobbyViewPanel rightLobbyViewPanel;
	private ListOfPlayersPanel listOfPlayersPanel;
	
	public LobbyView(String[][] board, Map<String, String> state, List<Message> messages) 
	{
		LobbyFrame = new JFrame();
		rightLobbyViewPanel = new RightLobbyViewPanel();
		listOfPlayersPanel = new ListOfPlayersPanel(state);
		setup(board, state, messages);
	}
	
	private void setup(String[][] board, Map<String, String> state, List<Message> messages) 
	{
		LobbyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ArrayList<String> ListOfRooms = new ArrayList<String>();
		ListOfRooms.add("Room1");
		ListOfRooms.add("Room2");
		ListOfRooms.add("Room3");
		ListOfRooms.add("Room4");
		ListOfRooms.add("Room5");
		ListOfRooms.add("Room6");
	 	JLabel ListOfRoomsText= new JLabel("List Of Rooms");
	 	rightLobbyViewPanel.add( ListOfRoomsText);
	 	
	 	rightLobbyViewPanel.setLayout(new BoxLayout(rightLobbyViewPanel, BoxLayout.Y_AXIS));

	 	for (String PlayerName : ListOfRooms) {
	 		JButton RoomButton = new JButton(PlayerName);
	 		RoomButton.addActionListener(new LobbyPlayerButtonActionListener(PlayerName));
	 		rightLobbyViewPanel.add(RoomButton, BorderLayout.CENTER);
		}
		
		
		LobbyFrame.getContentPane().add(rightLobbyViewPanel);
		
		/*
		JTextArea ta = new JTextArea("Hellossss ssssss ssssss ssssss  sssssss ssssssssssss sssssssssssssss  ssssssssss sssssssssssss sssssss sssssss ffffffffffffff ffffffffffffssssss");
		scorePanel.add(ta); 
		JTextArea ba = new JTextArea("World");
		scorePanel.add(ba); 
		scorePanel.setBackground(Color.yellow);
		*/
 	
		 	ArrayList<String> ListOfPlayers = new ArrayList<String>();
		 	ListOfPlayers.add("Billy");
		 	ListOfPlayers.add("Sally");
		 	ListOfPlayers.add("Bobly");
		 	ListOfPlayers.add("Sely");
		 	ListOfPlayers.add("Lilly");
		 	ListOfPlayers.add("Rily");
		 	JLabel ListOfPlayersText= new JLabel("List Of Players");
		 	listOfPlayersPanel.add(ListOfPlayersText);
		 	
		 	// This will be the map that is imported from the client
		 	listOfPlayersPanel.setLayout(new BoxLayout(listOfPlayersPanel, BoxLayout.Y_AXIS));

		 	for (String PlayerName : ListOfPlayers) {
		 		JButton PlayerButton = new JButton(PlayerName);
		 		PlayerButton.addActionListener(new LobbyPlayerButtonActionListener(PlayerName));
		 		listOfPlayersPanel.add(PlayerButton, BorderLayout.CENTER);
			}
		 	/*//Testing List Of Players adding to lobby panel
		 	


			
		 	
	        textPane.setEditable( false );
	        textPane.setPreferredSize( new Dimension(120, 23) );
		*/
		 	LobbyFrame.getContentPane().add(listOfPlayersPanel, BorderLayout.WEST);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) { }
		SwingUtilities.updateComponentTreeUI(LobbyFrame);
		LobbyFrame.pack();
		LobbyFrame.setVisible(true);

	}
	
	public boolean update(String[][] board, Map<String, String> state, List<Message> messages) //Will be replaced w/ SimplifiedModel
	{
		if(rightLobbyViewPanel.update() && listOfPlayersPanel.update(state))
		{
			LobbyFrame.revalidate();
			LobbyFrame.repaint();
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
		messages.add(new Message("Test message"));
		LobbyView hws = new LobbyView(board, state, messages);
		
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
