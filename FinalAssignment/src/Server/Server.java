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
	  Gson gson = new Gson();
	  
	  NetworkMessage j1 = gson.fromJson(jsonString, NetworkMessage.class);
	  
	  Vector<Command> commands = j1.getCommands();
	  for(Command c: commands)
		  System.out.println(c.getType()+"   "+c.getContent());
  
	  
  }

	/**
	 * run method services each request in a separate thread.
	 */
	public void run() {
		BufferedReader in = null;
		DataOutputStream dataOut = null;

		try {

			String input, output;

			//get character input stream from client
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			//get binary output stream to client (for requested data)
			dataOut = new DataOutputStream(connect.getOutputStream());

			output = "";

			while((input = in.readLine()) != null){
				if(input.startsWith("Content-Length")){
					input = in.readLine();
					input = in.readLine();
					break;
				}
			}
			
			processRequest(input);


			//send HTTP headers
			dataOut.writeBytes("HTTP/1.0 200 OK"+"\r\n");
			dataOut.writeBytes("Content-Type: text/html" + "\r\n");
			dataOut.writeBytes("Connection: close" + "\r\n");
			dataOut.writeBytes("Content-length: "+output.length()+"\r\n\r\n");
			dataOut.writeBytes(output);
		}
		catch (IOException ioe) {
			System.err.println("Server Error: " + ioe);
		}
		finally {
			try {
				in.close();
				dataOut.flush();
				dataOut.close();
				connect.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
