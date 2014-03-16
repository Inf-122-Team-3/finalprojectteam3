package Client;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import Game.GameFactory;
import Model.Model;
import Util.Command;
import Util.Invite;
import Util.NetworkMessage;
import Util.Player;
import View.LobbyView;

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
	LobbyView view;

	public Client(String username, LobbyView view){
		this.view = view;
		username = username.toUpperCase();
		Json = new Gson();
		try  {
			kkSocket = new Socket(HOSTNAME, PORT);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

			Vector<Command> commands = new Vector<>();
			commands.add(new Command("#SIGNIN", username));
			sendCommand(commands);

			new Receptor(in).start();

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + HOSTNAME);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + HOSTNAME);
			System.exit(1);
		}
	}

	//Remove, for Testing only
	public static void main(String[] args) {
		//testing code
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an username:");
		String username = keyboard.nextLine();
		Client client = new Client(username, null);
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
		Vector<Command> v = new Vector<>();
		v.add(new Command("#GETLISTOFGAMES", ""));
		sendCommand(v);
	}

	private void setGamesAvailable(Vector<String> v){
		//view.setGamesAvailable(v);
	}

	public void sendInvite(String game, String opponent){
		Vector<Command> v = new Vector<>();
		Vector<String> v2 = new Vector<>();
		v2.add(game);
		v2.add(opponent);
		v2.add(player.getUsername());
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

	private void updateActivePlayers(Vector<String> v){
		System.out.println("Players Available:");
		for(String s: v)
			System.out.println(s);
		//view.updateActivePlayers(v);
	}

	private void updateView(){
		//view.updateView(model);
	}

	private void startGame(GameFactory gameFactory, List<Player> listOfPlayers){
		//view.startGame(model);
	}

	private void receiveInvitation(String username, String game){
		System.out.println("Recieved invite from " + username + " for " + game);
		//view.invitation();
	}

	public void processMessage(String jsonString){
		NetworkMessage msg_from_server = new NetworkMessage(jsonString);
		//System.out.println("Message received from SERVER");
		//System.out.println(msg_from_server.toString());
		for(Command c: msg_from_server.getCommands()) {
			//Server repsonse for signing in
			if(c.getType().equals("#SIGNIN")){
				if(!c.getFail()){
					this.player = Json.fromJson((String) c.getContent(), Player.class);
					System.out.println("SIGNIN SUCESSUFUL ID ="+this.player.getId());
					sendInvite("Test", "theonepoofei");
				}
				else{
					System.out.println("Fail: "+c.getContent());
				}
			}
			//Server repsonse for starting a game
			else if(c.getType().equals("#STARTGAME")){
				Vector<String> content = Json.fromJson(c.getContent(), Vector.class);
				GameFactory gameFactory = Json.fromJson(content.get(0), GameFactory.class);
				List<Player> listOfPlayers = Json.fromJson(content.get(1), List.class);
				
				startGame(gameFactory, listOfPlayers);
			}
			//Server repsonse for move being made in game
			else if(c.getType().equals("#CLICK")){
				updateView();
			}
			//Server response for getting active players
			else if(c.getType().equals("#GETAVAILABLEPLAYERS")){
				updateActivePlayers((Vector<String>)(Json.fromJson(c.getContent(), Vector.class)));
			}
			//Updating game view
			else if(c.getType().equals("#MODELUPDATED")){
				updateView();
			}
			//Server sent an invitation, someone invited user to game
			else if(c.getType().equals("#INVITATION")) {
				Invite invite = Json.fromJson(c.getContent(), Invite.class);
				receiveInvitation(invite.getSenderUsername(), invite.getGame());
			}
			//Server repsonse, invite repsonse from other player
			else if(c.getType().equals("#INVITERESPONSE")) {
				Invite invite = Json.fromJson(c.getContent(), Invite.class);
				receiveInvitation(invite.getSenderUsername(), invite.getGame());
			}
			//Get list of ongoing games
			else if(c.getType().equals("#GETLISTGAMES")){
				setGamesAvailable((Vector<String>)(Json.fromJson(c.getContent(), Vector.class)));
			}
		}	
	}
}
