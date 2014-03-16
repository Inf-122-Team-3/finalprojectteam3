package Client;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

import Util.Command;
import Util.NetworkMessage;
import Util.Player;

import com.google.gson.Gson;

public class Client {
	class Receptor extends Thread{
		BufferedReader in;
		
		public Receptor(BufferedReader in) {
			this.in = in;
		}
		
		public void run(){
			String fromServer;
			try {
				while ((fromServer = in.readLine()) != null) {
					processMessage(fromServer);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	static String HOSTNAME = "127.0.0.1";	//localhost	
	static int PORT = 8080;	
	
	Socket kkSocket;
	PrintWriter out;
	BufferedReader in;
	Player player = null;
	Gson Json;
	IGameView view;
	
	public Client(String username, IGameView view)
	{
		this(username, HOSTNAME, PORT, view);
	}
	
	public Client(String username, String hostname, int port, IGameView view){
		this.view = view;
		username = username.toUpperCase();
		Json = new Gson();
		try  {
			kkSocket = new Socket(hostname, port);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
			
			Vector<Command> commands = new Vector<>();
			commands.add(new Command("#SIGNIN", username));
			sendCommand(commands);
			
			new Receptor(in).start();
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostname);
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		//testing code
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an username:");
		String username = keyboard.nextLine();
		Client client = new Client(username, null);
		client.getListAvailablePlayers();
	}
	
	private void sendCommand(Vector<Command> commands){
		System.out.println("sendCommand");
		NetworkMessage m = new NetworkMessage((this.player != null ? this.player.getId() : -1));
		m.setCommands(commands);
		
		out.println(m.toJson());
	}
	
	public void getListAvailablePlayers(){
		System.out.println("getListAvailablePlayers");
		Vector<Command> v = new Vector<>();
		v.add(new Command("#GETAVAILABLEPLAYERS", ""));
		sendCommand(v);
	}
	

	public void getListGames(){
		
	}
	
	public void sendInvite(String game, String opponent){
		Vector<Command> v = new Vector<>();
		Vector<String> v2 = new Vector<>();
		v2.add(game);
		v2.add(opponent);
		v.add(new Command("#INVITE", Json.toJson(v2)));
		sendCommand(v);
	}
	
	public void respondInvitation(int codeInvitation, Boolean accept){
		Vector<Command> v = new Vector<>();
		v.add(new Command("#ACCEPTINVITE", Json.toJson(codeInvitation)));
		sendCommand(v);
	}
	
	public void click(Point coordinate){
		Vector<Command> v = new Vector<>();
		v.add(new Command("#CLICK", Json.toJson(coordinate)));
		sendCommand(v);
	}
	
	public void processMessage(String jsonString){
		NetworkMessage msg_from_server = new NetworkMessage(jsonString);
		
		System.out.println("Message received from SERVER");
		System.out.println(msg_from_server.toString());
		
		for(Command c: msg_from_server.getCommands()){
			  if(c.getType().equals("#SIGNIN")){
				  if(!c.getFail()){
					  this.player = Json.fromJson((String) c.getContent(), Player.class);
					  System.out.println("SIGNIN SUCESSUFUL ID ="+this.player.getId());
				  }
				  else{
					  System.out.println("Fail: "+c.getContent());
				  }
			  }
			  else if(c.getType().equals("#STARTGAME")){
				  startGame();
			  }
			  else if(c.getType().equals("#CLICK")){
				  updateView();
			  }
			  else if(c.getType().equals("#GETAVAILABLEPLAYERS")){
				  updateActivePlayers((Vector<String>)(Json.fromJson(c.getContent(), Vector.class)));
			  }
			  else if(c.getType().equals("#MODELUPDATED")){
				  updateView();
			  }
			  else if(c.getType().equals("#INVITATION")){
				  receiveInvitation("aa", "aa");
			  }
			  else if(c.getType().equals("#GETLISTGAMES")){
				  setGamesAvailable((Vector<String>)(Json.fromJson(c.getContent(), Vector.class)));
			  }
		  }	
	}
	
	private void updateActivePlayers(Vector<String> v){
		System.out.println("Players Available:");
		for(String s: v)
			System.out.println(s);
		//view.updateActivePlayers(v);
	}
	
	private void startGame(){
		
		view.startGame();
	}
	
	private void updateView(){
		
		
		view.updateView();
	}
	
	private void setGamesAvailable(Vector<String> v){

		
		view.setGamesAvailable(v);
	}
	
	private void receiveInvitation(String username, String game){
		
		view.receiveInvitation(username, game);
	}
	
}
