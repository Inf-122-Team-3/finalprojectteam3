package Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
   	Socket connect;

	//constructor
	public Server(Socket connect) {
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
  
  public Vector<Command> processRequest(String jsonString){
	  /*JsonMessage j = new JsonMessage("ariel", new Model());
	  Command c = new Command("initGame", true);
	  j.addCommand(c);
	  
	  Gson gson = new Gson();
	  String json = gson.toJson(j);
	  */
	  
	  Gson gson = new Gson();
	  
	  NetworkMessage j1 = gson.fromJson(jsonString, NetworkMessage.class);
	  
	  Vector<Command> commands = j1.getCommands();
	  for(Command c: commands)
		  System.out.println(c.getType()+"   "+c.getContent());
	  
	  return j1.getCommands();
	  
  }

	/**
	 * run method services each request in a separate thread.
	 */
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		DataOutputStream dataOut = null;

		try {

			String input, output;

			//get character input stream from client
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			//get binary output stream to client (for requested data)
			dataOut = new DataOutputStream(connect.getOutputStream());

			//Sends on first connect
			output = "Hello From Server"+"\r\n\r\n";

			//Waits for commands, put protocol here
			int i = 0;
			
			while(in.ready()){
				input = in.readLine();
				System.out.println(i+" "+input);
				input = in.readLine();
			}
			
			//BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			/*StringBuilder stringBuilder = new StringBuilder();
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            //in.r
            while ((bytesRead = in.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
            
            System.out.println(stringBuilder);*/
			/*System.out.println("here "+in.ready());
			while(in.ready()){
				input = in.readLine();
				System.out.println(i+" "+input);
				input = in.readLine();
			}*/
			/*while (in.ready()) {
				input = in.readLine();
				System.out.println(i+" "+input);
				i++;
				/*if(input.startsWith("Content-Length:")){
					input = in.readLine();
					System.out.println("first line: "+input);
					input = in.readLine();
					System.out.println("second line: "+input);
				}*/
				/*Vector<Command> commands = processRequest(input);
				
				for(Command c:commands)
					System.out.println(c.getType()+"  "+c.getContent());*/
				
			//}
			
			//System.out.println("After read lines");

			//String currentLine = in.readLine();
			//String headerLine = currentLine;        
			//StringTokenizer tokenizer = new StringTokenizer(headerLine);
			//String httpMethod = tokenizer.nextToken();
			//String httpQueryString = tokenizer.nextToken();

			//      if (httpMethod.equals("GET")) {
			//   	    System.out.println("GET request");
			//		// The test home page
			//		responseString = 
			//				"<html><body>" +
			//						"<form action=\"http://127.0.0.1:8080\" enctype=\"multipart/form-data\" method=\"post\">" +
			//							"Enter the name <input name=\"file\" type=\"text\"><br>" +
			//							"<input value=\"Submit\" type=\"submit\"></form>"
			//			   + "</body></html>";           
			//      }
			//      else{//POST
			//    	  System.out.println("POST request");
			//      }

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
				dataOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
