package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import Game.GameFactory;
import Game.GameInstance;
import Game.Connect4.Connect4Factory;
import Game.TicTacToe.TicTacToeFactory;
import Util.Command;
import Util.IDGenerator;
import Util.Invite;
import Util.NetworkMessage;
import Util.Player;

import com.google.gson.Gson;

public class Server
{
	static final int PORT = 8080; //default port

	//Holds server data on players, and available games
	Map<String, Socket> connectedClients;
	Map<String, Player> activePlayers;
	Map<String, Player> availablePlayers;
	Vector<Player> allPlayers;
	Map<String, GameFactory> gameMap;
	Map<Integer, GameInstance> currentGames;
	
	Map<Integer, Socket> playerSockets;
	BufferedReader in = null;
	PrintWriter dataOut = null;
	Map<Integer, ServerThread> threads;
	Gson Json;

	//constructor
	public Server() {
		this.activePlayers = new HashMap<String, Player>();
		this.availablePlayers = new HashMap<String, Player>();
		this.allPlayers = new Vector<Player>();
		this.gameMap = new HashMap<String, GameFactory>();
		this.currentGames = new HashMap<Integer, GameInstance>();
		this.threads = new HashMap<Integer, ServerThread>();
		this.connectedClients = new HashMap<String, Socket>();
		this.Json = new Gson();
		
		buildGameMap();
	}

	private void buildGameMap() {
		gameMap.put("Connect4", new Connect4Factory());
		gameMap.put("TicTacToe", new TicTacToeFactory());
		gameMap.put("Gomuku", null);
	}
	
	/**
	 * main method creates a new HttpServer instance for each
	 * request and starts it running in a separate thread.
	 */
	public static void main(String[] args) {
		Server server = new Server();

		try {
			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("\nListening for connections on port " + PORT + "...\n");
			//listen until user halts execution
			while (true) {
				new ServerThread(server, serverConnect.accept()).start();
			}
		}
		catch (IOException e) {
			System.err.println("Server error: " + e);
		}
	}

	static public class ServerThread extends Thread{
		Server server;
		Socket socket;
		BufferedReader in = null;
		PrintWriter dataOut = null;
		GameInstance ongoingGame = null;

		public ServerThread(Server server, Socket soc){
			this.server = server;
			this.socket = soc;	

			//create buffers input and output
			try {
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				dataOut = new PrintWriter(this.socket.getOutputStream(), true);
				//System.out.println(soc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void processRequest(String jsonString){
			System.out.println("Message received from client: "+jsonString);
			NetworkMessage msg_from_client = this.server.Json.fromJson(jsonString, NetworkMessage.class);
			NetworkMessage msg_to_client = new NetworkMessage();

			for(Command c: msg_from_client.getCommands()){
				if(c.getType().equals("#SIGNIN")){
					System.out.println("User "+c.getContent()+" signing in");

					boolean fail = false;
					boolean playerExists = false;
					for(Player p: this.server.activePlayers.values()){
						//user already signed in
						if(p.getUsername().equals(c.getContent())){
							msg_to_client.addCommand(new Command("#SIGNIN", "Username already in use", true));
							fail = true;
							break;
						}
					}

					//Loads player
					if(!fail) {
						for(Player p : this.server.allPlayers) {
							if(p.getUsername().compareTo(c.getContent()) == 0) {
								msg_to_client.addCommand(new Command("#SIGNIN", this.server.Json.toJson(p,Player.class)));
								
								server.availablePlayers.put(p.getUsername(), p);
								server.activePlayers.put(p.getUsername(), p);
								this.server.connectedClients.put(p.getUsername(), socket);
								
								playerExists = true;
								break;
							}
						}
					}

					if(!fail && !playerExists){
						Player p = new Player(IDGenerator.getNextID(), (String) c.getContent());

						msg_to_client.addCommand(new Command("#SIGNIN", this.server.Json.toJson(p,Player.class)));

						server.allPlayers.add(p);
						server.availablePlayers.put(p.getUsername(), p);
						server.activePlayers.put(p.getUsername(), p);
						
						this.server.connectedClients.put(p.getUsername(), socket);
					}
				}
				
				else if(c.getType().equals("#INVITE")){
					Vector<String> contentData = (Vector<String>) this.server.Json.fromJson( c.getContent(), Vector.class);
					for(String key : this.server.connectedClients.keySet())
					{
						System.out.println(key);
						if(contentData.get(1).compareToIgnoreCase(key) == 0)
						{
							//Send invite via socket
							Socket sendTo = this.server.connectedClients.get(key);
							//New vector for data to be sent
							Invite invite = new Invite(contentData.get(2), contentData.get(1), contentData.get(0));
							
							NetworkMessage invitiation = new NetworkMessage();
							invitiation.addCommand(new Command("#INVITATION", this.server.Json.toJson(invite)));
							
							try {
								new PrintWriter(sendTo.getOutputStream(), true).println(invitiation.toJson());
							} catch (IOException e) {
								System.err.println("Could not send to this socket");
							}
						}
					}

				}
				
				else if(c.getType().equals("#CLICK")){
					//to implement, call game logic moves
				}
				
				else if(c.getType().equals("#GETAVAILABLEPLAYERS")){
					System.out.println("#GETAVAILABLEPLAYERS");
					Vector<String> v = new Vector<>();
					
					for(Player p: this.server.availablePlayers.values())
						v.add(p.getUsername());

					msg_to_client.addCommand(new Command("#GETAVAILABLEPLAYERS", this.server.Json.toJson(v)));
				}
				
				else if(c.getType().equals("#GETLISTOFGAMES")){
					Vector<String> v = new Vector<>();
					for(String gameName : this.server.gameMap.keySet()) {
						v.add(gameName);
					}

					msg_to_client.addCommand(new Command("#GETLISTOFGAMES", this.server.Json.toJson(v)));
				}
				
				else if(c.getType().equals("#INVITERESPONSE")){
					//to implement, reply
					Vector<String> contentData = (Vector<String>) this.server.Json.fromJson( c.getContent(), Vector.class);
					Invite invite = this.server.Json.fromJson(contentData.get(0), Invite.class);
					if(invite.getAccepted()) {
						List<Player> listOfPlayers = new ArrayList<Player>();
						Player player1 = getPlayer(invite.getSenderUsername());
						Player player2 = getPlayer(invite.getRecipientUsername());
						
						listOfPlayers.add(player1);
						listOfPlayers.add(player2);
						
						//Start game
						GameFactory gameFactory = this.server.gameMap.get(invite.getGame());
						if(gameFactory.getClass().equals(TicTacToeFactory.class)) {
							gameFactory = new TicTacToeFactory();
							ongoingGame = gameFactory.createGame(listOfPlayers);
						}
						else if(gameFactory.getClass().equals(Connect4Factory.class)) {
							gameFactory = new Connect4Factory();
							ongoingGame = gameFactory.createGame(listOfPlayers);
						}
						
						//Sends the model
//						if(ongoingGame != null) {
//							Command command = new Command("#STARTGAME", this.server.Json.toJson(ongoingGame.getModel()));
//							msg_to_client.addCommand(command);
//						}
						
						//Sends the game factory with a list of players
						Vector<String> toSendData = new Vector<String>();
						if(gameFactory != null) {
							toSendData.add(this.server.Json.toJson(gameFactory));
							toSendData.add(this.server.Json.toJson(listOfPlayers));
							Command command = new Command("#STARTGAME", this.server.Json.toJson(toSendData));
							msg_to_client.addCommand(command);
						}
					}
					else {
						//Send message to sender saying user declined
						
					}
				}
			}

			dataOut.println(msg_to_client.toJson());
		}
		
		private Player getPlayer(String username) {
			for(Player p : this.server.allPlayers) {
				if(p.getUsername().compareTo(username) == 0) {
					return p;
				}
			}
			
			return null;
		}

		@Override
		public void run() {
			String input;
			try {
				while((input = in.readLine()) != null){
					processRequest(input);
				}
			} catch (IOException e) {
				System.out.println(socket + " has discconected");
				//e.printStackTrace();
			}
			finally{
				try {
					in.close();
					dataOut.close();
					socket.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
		}
	}
}
