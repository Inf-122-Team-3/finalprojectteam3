package Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import Model.Model;
import Util.Command;
import Util.IDGenerator;
import Util.NetworkMessage;
import Util.Player;
import Game.GameFactory;
import Game.GameInstance;

import com.google.gson.Gson;

public class Server
{
	static final int PORT = 8080; //default port
	
	Map<Integer, Player> activePlayers;
	Map<Integer, Player> availablePlayers;
	Vector<Player> allPlayers;
	Map<String, GameFactory> gameMap;
	Map<Integer, GameInstance> currentGames;
	Map<Integer, Socket> playerSockets;
   	BufferedReader in = null;
   	PrintWriter dataOut = null;
	Map<Integer, ServerThread> threads;
	
	//constructor
	public Server() {
		this.activePlayers = new HashMap<Integer, Player>();
		this.availablePlayers = new HashMap<Integer, Player>();
		this.allPlayers = new Vector<Player>();
		this.gameMap = new HashMap<String, GameFactory>();
		this.currentGames = new HashMap<Integer, GameInstance>();
		this.threads = new HashMap<Integer, ServerThread>();
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
	   	
		public ServerThread(Server server, Socket soc){
			this.server = server;
			this.socket = soc;	
			
			//create buffers input and output
			try {
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				dataOut = new PrintWriter(this.socket.getOutputStream(), true);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    public void processRequest(String jsonString){
	  	  Gson gson = new Gson();
	  	  
	  	  NetworkMessage msg_client = gson.fromJson(jsonString, NetworkMessage.class);
	  	  
	  	  for(Command c: msg_client.getCommands()){
	  		  if(c.getType().equals("#SIGNIN")){
	  			  System.out.println("User "+c.getContent()+" signing in");
	  			  
	  			  NetworkMessage msg = new NetworkMessage(IDGenerator.getNextID(), null);
	  			  
	  			  boolean fail = false;
	  			  for(Player p: this.server.activePlayers.values()){
	  				  //user already signed in
	  				  if(p.getUsername().equals(c.getContent())){
	  					  msg.addCommand(new Command("#SIGNIN", "Username already in use", true));
	  					  fail = true;
	  					  break;
	  				  }
	  		  	  }
	  			  
	  			  if(!fail){//create a new player
	  				  Player p = new Player(IDGenerator.getNextID(), (String) c.getContent());
	  				  
	  				  msg.addCommand(new Command("#SIGNIN", gson.toJson(p,Player.class)));
	  				  
	  				  server.allPlayers.add(p);
	  				  server.availablePlayers.put(p.getId(), p);
	  				  server.activePlayers.put(p.getId(), p);
	  				  
	  			  }
	  			  
	  			  dataOut.println(msg.toJson());
	  		  }		  
	  	  }	  
	    }
		
		@Override
		public void run() {
			String input;
			try {
				while((input = in.readLine()) != null){
					processRequest(input);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					in.close();
					dataOut.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
