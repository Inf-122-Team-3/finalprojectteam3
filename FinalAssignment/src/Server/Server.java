package Server;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import Game.Chess.ChessFactory;
import Game.Connect4.Connect4Factory;
import Game.Gomoku.GomokuFactory;
import Game.TicTacToe.TicTacToeFactory;
import Model.Model;
import Model.ModelSimplifier;
import Util.Command;
import Util.IDGenerator;
import Util.Invite;
import Util.NetworkMessage;
import Util.Player;
import Util.SimplifiedModel;

import com.google.gson.Gson;

public class Server
{
	static final int PORT = 8080; //default port

	//Holds server data on players, and available games
	Map<String, Socket> connectedClients;
	Map<String, Player> activePlayers;
	Map<String, Player> availablePlayers;
	static Vector<Player> allPlayers;
	Map<String, GameFactory> gameMap;
	Map<Integer, GameInstance> currentGames;

	Map<Integer, Socket> playerSockets;
	BufferedReader in = null;
	PrintWriter dataOut = null;
	Map<Integer, ServerThread> threads;
	static Gson Json;

	static FileWriter fw;
	static BufferedWriter bw;
	//constructor

	public Server() {
		this.activePlayers = new HashMap<String, Player>();
		this.availablePlayers = new HashMap<String, Player>();
		Server.allPlayers = new Vector<Player>();
		this.gameMap = new HashMap<String, GameFactory>();
		this.currentGames = new HashMap<Integer, GameInstance>();
		this.threads = new HashMap<Integer, ServerThread>();
		this.connectedClients = new HashMap<String, Socket>();
		Server.Json = new Gson();

		buildGameMap();
	}

	private void buildGameMap() {
		gameMap.put("Connect4", new Connect4Factory());
		gameMap.put("TicTacToe", new TicTacToeFactory());
		gameMap.put("Gomuku", new GomokuFactory());
		//no chess
		//gameMap.put("Chess", new ChessFactory());
	}

	/**
	 * main method creates a new HttpServer instance for each
	 * request and starts it running in a separate thread.
	 */
	public static void main(String[] args) {
		final Server server = new Server();

		try {
			//Load player data
			File file = new File("playerData.txt");
			if(!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);

			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("\nListening for connections on port " + PORT + "...\n");

			while(true)  {
				new ServerThread(server, serverConnect.accept()).start();
			}
		}
		catch (IOException e) {
			System.err.println("Server error: " + e);
		}
	}

	//Starts the server, reads the saved player data, and starts new Listen thread
	public static void startServer() {
		try {
			Server server = new Server();
			ServerSocket serverConnect = new ServerSocket(PORT);

			//Load player data
			File file = new File("playerData.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String currentLine;
			while((currentLine = br.readLine()) != null) {
				//System.out.println(currentLine);
				Player p = Server.Json.fromJson(currentLine, Player.class);
				allPlayers.add(p);
			}

			new Listen(server, serverConnect).start();
		}
		catch (IOException e) {
			System.err.println("Server error: " + e);
		}
	}

	//Stops the server, and writes all the player data to a txt file in JSON format
	public static void stopServer() {
		try {
			File file = new File("playerData.txt");
			if(file.exists()) {
				file.delete();
			}

			file.createNewFile();

			fw = new FileWriter(file, false);
			bw = new BufferedWriter(fw);

			for(Player p : allPlayers) {
				bw.write(Server.Json.toJson(p));
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {

		}
		System.exit(0);
	}

	//Listen for new connections
	static public class Listen extends Thread {
		Server server;
		ServerSocket serverSocket;

		public Listen(Server server, ServerSocket serverSocket) {
			this.server = server;
			this.serverSocket = serverSocket;
		}

		@Override
		public void run() {
			System.out.println("\nListening for connections on port " + PORT + "...\n");

			while(true)  {
				try {
					new ServerThread(server, serverSocket.accept()).start();
				} catch (IOException e) {
				}
			}
		}
	}

	//Thread for each connected client
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

		public void processRequest(String jsonString) throws IOException{
			//System.out.println("Message received from client: "+jsonString);
			NetworkMessage msg_from_client = this.server.Json.fromJson(jsonString, NetworkMessage.class);
			NetworkMessage msg_to_client = new NetworkMessage();

			for(Command c: msg_from_client.getCommands()){
				if(c.getType().equals("#SIGNIN")){
					boolean fail = false;
					boolean playerExists = false;
					//Looks if the player is already logged in, if it is, ends search and sets fail tag to notify user of error
					for(Player p: this.server.activePlayers.values()){
						//user already signed in
						if(p.getUsername().equals(c.getContent())){
							msg_to_client.addCommand(new Command("#SIGNIN", "Username already in use", true));
							fail = true;
							break;
						}
					}

					//If user is not signed it, look if profile exists
					//Loads that player profile
					if(!fail) {
						for(Player p : Server.allPlayers) {
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

					//If player is not signed in, and does not exist, creates new player profile
					if(!fail && !playerExists){
						Player p = new Player(IDGenerator.getNextID(), (String) c.getContent());
						msg_to_client.addCommand(new Command("#SIGNIN", this.server.Json.toJson(p,Player.class)));

						Server.allPlayers.add(p);
						server.availablePlayers.put(p.getUsername(), p);
						server.activePlayers.put(p.getUsername(), p);

						this.server.connectedClients.put(p.getUsername(), socket);
					}
				}

				//Invitation was sent by a user
				else if(c.getType().equals("#INVITE")){
					Vector<String> contentData = (Vector<String>) this.server.Json.fromJson( c.getContent(), Vector.class);
					for(String key : this.server.connectedClients.keySet())
					{
						//System.out.println(key);
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

				else if(c.getType().equals("#INVITERESPONSE")){
					//to implement, reply
					Invite invite = Server.Json.fromJson(c.getContent(), Invite.class);
					
					if(invite.getAccepted()) {
						List<Player> listOfPlayers = new ArrayList<Player>();
						Player player1 = getPlayer(invite.getSenderUsername());
						Player player2 = getPlayer(invite.getRecipientUsername());

						listOfPlayers.add(player1);
						listOfPlayers.add(player2);

						//Start game
						GameFactory gameFactory = this.server.gameMap.get(invite.getGame());
						
						//Sends the model factory with a list of players
						//Sends to both clients and starts game if accepted
						Vector<String> toSendData = new Vector<String>();
						if(gameFactory != null) {
							//Build the model to pass back to game view
							GameInstance gameInstance = gameFactory.createGame(listOfPlayers);
							Model gameModel = gameInstance.getModel();
							int gameKey = Util.IDGenerator.getNextID();
							
							server.currentGames.put(gameKey, gameInstance);
							
							SimplifiedModel model = ModelSimplifier.simplify(gameModel, null);
							
							toSendData.add(Server.Json.toJson(model));
							toSendData.add(Server.Json.toJson(gameInstance.getPlayers()));
							toSendData.add(Server.Json.toJson(gameKey));
							
							//Send the data to both players
							Command command = new Command("#STARTGAME", this.server.Json.toJson(toSendData));

							NetworkMessage newGameMessage = new NetworkMessage();
							newGameMessage.addCommand(command);

							Socket player1Socket = getPlayerSocket(player1.getUsername());
							Socket player2Socket = getPlayerSocket(player2.getUsername());

							try {
								new PrintWriter(player1Socket.getOutputStream(), true).println(newGameMessage.toJson());
								new PrintWriter(player2Socket.getOutputStream(), true).println(newGameMessage.toJson());
							} catch (IOException e) {
								System.err.println("Could not send to this socket");
							}
						}
					}
					else {
						//Sends declined notice to user who started invite
						Socket sendTo = getPlayerSocket(invite.getSenderUsername());
						NetworkMessage inviteDeclined = new NetworkMessage();
						inviteDeclined.addCommand(new Command("#INVITEDECLINED", this.server.Json.toJson(invite)));
						try {
							new PrintWriter(sendTo.getOutputStream(), true).println(inviteDeclined.toJson());
						} catch (IOException e) {
							System.err.println("Could not send to this socket");
						}
					}
				}
				
				//Sets the game that is currently running on the thread
				else if(c.getType().equals("#GAMESTARTED")){ 
					int gameKey = new Gson().fromJson(c.getContent(), int.class);
					ongoingGame = server.currentGames.get(gameKey);
					
					System.out.println("Game has started between " + ongoingGame.getPlayers().get(0).getUsername() + " and " + ongoingGame.getPlayers().get(1).getUsername());
				}

				else if(c.getType().equals("#CLICK")){
					//to implement, call game logic moves
					Vector<String> contentData = (Vector<String>) this.server.Json.fromJson( c.getContent(), Vector.class);
					Point coordinate = this.server.Json.fromJson(contentData.get(0), Point.class);
					String username = this.server.Json.fromJson(contentData.get(1), String.class);

					int x = (int) coordinate.getX();
					int y = (int) coordinate.getY();
					Player p = getPlayer(username);

					Model updatedModel = ongoingGame.update(x, y, p);
					SimplifiedModel model = ModelSimplifier.simplify(updatedModel, p);
					
					//Send the data to both players
					Command command = new Command("#MODELUPDATED", new Gson().toJson(model));

					NetworkMessage modelmessage = new NetworkMessage();
					modelmessage.addCommand(command);

					//Gets the two players
					Player player1 = ongoingGame.getPlayers().get(0);
					Player player2 = ongoingGame.getPlayers().get(1);
					
					//Gets their socket to send two
					Socket player1Socket = getPlayerSocket(player1.getUsername());
					Socket player2Socket = getPlayerSocket(player2.getUsername());

					try {
						new PrintWriter(player1Socket.getOutputStream(), true).println(modelmessage.toJson());
						new PrintWriter(player2Socket.getOutputStream(), true).println(modelmessage.toJson());
					} catch (IOException e) {
						System.err.println("Could not send to this socket");
					}
					
				}

				else if(c.getType().equals("#GETAVAILABLEPLAYERS")){
					//System.out.println("#GETAVAILABLEPLAYERS");
					Vector<String> v = new Vector<>();
					for(Player p: this.server.activePlayers.values())
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

				else if(c.getType().equals("#DISCONNECT")){
					String username = c.getContent();
					for(Player p: this.server.activePlayers.values()){
						//find the signed in user in active players, and remove
						if(p.getUsername().equals(username)){
							this.server.activePlayers.remove(username);
						}
					}
				}
			}
			dataOut.println(msg_to_client.toJson());
		}

		private Player getPlayer(String username) {
			for(Player p : Server.allPlayers) {
				if(p.getUsername().compareTo(username) == 0) {
					return p;
				}
			}
			return null;
		}

		private Socket getPlayerSocket(String username) {
			return this.server.connectedClients.get(username);
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
