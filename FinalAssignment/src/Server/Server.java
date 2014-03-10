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

public class Server implements Runnable
{
	static final int PORT = 8080; //default port
	
	Map<Integer, Player> activePlayers;
	Map<Integer, Player> availablePlayers;
	Vector<Player> allPlayers;
	Map<String, GameFactory> gameMap;
	Map<Integer, GameInstance> currentGames;
	Map<Integer, Socket> playerSockets;
   	Socket connect;
   	BufferedReader in = null;
   	PrintWriter dataOut = null;
	

	//constructor
	public Server(Socket connect) {
		this.activePlayers = new HashMap<Integer, Player>();
		this.availablePlayers = new HashMap<Integer, Player>();
		this.allPlayers = new Vector<Player>();
		this.gameMap = new HashMap<String, GameFactory>();
		this.currentGames = new HashMap<Integer, GameInstance>();
		this.connect = connect;
	}

	/**
	 * main method creates a new HttpServer instance for each
	 * request and starts it running in a separate thread.
	 */
	public static void main(String[] args) {
		try {
			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("\nListening for connections on port " + PORT + "...\n");
			//listen until user halts execution
			while (true) {
				Server server = new Server(serverConnect.accept()); //instantiate HttpServer
				//create new thread
				Thread threadRunner = new Thread(server);
				threadRunner.start(); //start thread
			}
		}
		catch (IOException e) {
			System.err.println("Server error: " + e);
		}
    }
  
  public void processRequest(String jsonString){
	  System.out.println("Processing request client "+jsonString);
	  Gson gson = new Gson();
	  
	  NetworkMessage msg_client = gson.fromJson(jsonString, NetworkMessage.class);
	  
	  //Vector<Command> commands = j1.getCommands();
	  for(Command c: msg_client.getCommands()){
		  if(c.getType().equals("#SIGNIN")){
			  System.out.println("User "+c.getContent()+" signing in");
			  
			  NetworkMessage msg = new NetworkMessage(IDGenerator.getNextID(), null);
			  msg.addCommand(new Command("#SIGNEDIN", msg.getIdPlayer()));
			  
			  dataOut.println(msg.toJson());

		  }		  
	  }
  
	  
  }

	/**
	 * run method services each request in a separate thread.
	 */
	public void run() {
		try {
			String input;
			//get character input stream from client
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			//get binary output stream to client (for requested data)
			dataOut = new PrintWriter(connect.getOutputStream(), true);
			
			while((input = in.readLine()) != null){
				processRequest(input);
			}
		}
		catch (IOException ioe) {
			System.err.println("Server Error: " + ioe);
		}
		finally {
			try {
				in.close();
				dataOut.close();
				connect.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
