package Client;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import Game.GameFactory;
import GameViewAdaptor.Adaptor;
import Model.Model;
import Util.Command;
import Util.Invite;
import Util.NetworkMessage;
import Util.Player;
import View.LobbyView;
import View.Login;

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
	static String HOSTNAME = "127.0.0.1";	//change host name to ip of the server, use whatismyip.com	
	static int PORT = 8080;	

	Socket kkSocket;
	PrintWriter out;
	BufferedReader in;
	Player player = null;
	Gson Json;
	LobbyView lobbyView;
	Login loginView;
	
	String[] listOfGames;
	String[] listOfAvaliablePlayers;
	
	public Client(String username, Login loginView) {
		this.loginView = loginView;
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
	
	public void setLobbyView(LobbyView lobbyView)
	{
		this.lobbyView = lobbyView;
	}
	
	public void clientDisconnect() {
		Vector<Command> commands = new Vector<>();
		commands.add(new Command("#DISCONNECT", player.getUsername()));
		sendCommand(commands);
	}

	private void sendCommand(Vector<Command> commands){
		//System.out.println("sendCommand");
		NetworkMessage m = new NetworkMessage((this.player != null ? this.player.getId() : -1));
		m.setCommands(commands);
		out.println(m.toJson());
	}

	private void loadListAvailablePlayers(){
		System.out.println("getListAvailablePlayers");
		Vector<Command> v = new Vector<>();
		v.add(new Command("#GETAVAILABLEPLAYERS", ""));
		sendCommand(v);
	}

	private void loadListGames(){
		Vector<Command> v = new Vector<>();
		v.add(new Command("#GETLISTOFGAMES", ""));
		sendCommand(v);
	}

	public String[] getListOFAvaliablePlayers() {
		return listOfAvaliablePlayers;
	}
	
	public String[] getListOfGames() {
		return listOfGames;
	}

	private void updateActivePlayers(Vector<String> v){
		listOfAvaliablePlayers = new String[v.size()];
		for(int i = 0; i < v.size(); i++) {
			listOfAvaliablePlayers[i] = v.get(i);
			System.out.println(v.get(i));
		}
		if(lobbyView != null)
			lobbyView.updateListOfAvaliablePlayers(listOfAvaliablePlayers);
		//view.updateActivePlayers(v);
	}
	
	private void updateListOfGames(Vector<String> v) {
		listOfGames = new String[v.size()];
		for(int i = 0; i < v.size(); i++) {
			listOfGames[i] = v.get(i);
			System.out.println(v.get(i));
		}
		if(lobbyView != null)
			lobbyView.updateListOfGames(listOfGames);
	}
	
	public void refreshListOfPlayers() {
		loadListAvailablePlayers();
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
	
	private void receiveInvitation(Invite invite){
		//System.out.println("Recieved invite from " + username + " for " + game);
		if(lobbyView != null) {
			//Call lobbyview method here to show message 
			lobbyView.gameInvite(invite);
		}
	}
	
	private void inviteRepsonse(Invite invite) {
		if(lobbyView != null) {
			lobbyView.inviteDeclined(invite);
		}
	}

	public void respondInvitation(Invite invite) {
		Vector<Command> v = new Vector<>();
		//#INVITERESPONSE
		v.add(new Command("#INVITERESPONSE", Json.toJson(invite)));
		sendCommand(v);
	}

	public void click(Point coordinate){
		Vector<Command> v = new Vector<>();
		Vector<String> data = new Vector<String>();
		
		data.add(Json.toJson(coordinate));
		data.add(player.getUsername());
		
		v.add(new Command("#CLICK", Json.toJson(data)));
		sendCommand(v);
	}

	private void updateView(){
		//view.updateView(model);
	}

	private void startGame(GameFactory gameFactory, List<Player> listOfPlayers){
		//view.startGame(model);
		new Adaptor(gameFactory, listOfPlayers, this);
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
					
					loginView.loadLobbyView();
					loadListAvailablePlayers();
					loadListGames();
				}
				else{
					//Pop error messag.
					if(loginView != null) {
						loginView.popupMessage(c.getContent());;
					}
					System.out.println("Fail: "+c.getContent());
				}
			}
			//Server repsonse for starting a game
			else if(c.getType().equals("#STARTGAME")){
				Vector<String> content = Json.fromJson(c.getContent(), Vector.class);
				//GameFactory gameFactory = Json.fromJson(content.get(0), GameFactory.class);
				Model model = Json.fromJson(content.get(0), Model.class);
				List<Player> listOfPlayers = Json.fromJson(content.get(1), List.class);
				
				new Adaptor(model, listOfPlayers, this);
				//startGame(gameFactory, listOfPlayers);
			}
			//Server repsonse for move being made in game
			else if(c.getType().equals("#CLICK")){
				updateView();
			}
			//Server response for getting active players
			else if(c.getType().equals("#GETAVAILABLEPLAYERS")){
				updateActivePlayers((Vector<String>)(Json.fromJson(c.getContent(), Vector.class)));
			}
			//Get list of ongoing games
			else if(c.getType().equals("#GETLISTOFGAMES")){
				updateListOfGames((Vector<String>)(Json.fromJson(c.getContent(), Vector.class)));
			}
			//Updating game view
			else if(c.getType().equals("#MODELUPDATED")){
				updateView();
			}
			//Server sent an invitation, someone invited user to game
			else if(c.getType().equals("#INVITATION")) {
				Invite invite = Json.fromJson(c.getContent(), Invite.class);
				receiveInvitation(invite);
			}
			//Server repsonse, invite repsonse from other player
			else if(c.getType().equals("#INVITEDECLINED")) {
				Invite invite = Json.fromJson(c.getContent(), Invite.class);
				inviteRepsonse(invite);
			}
		}	
	}
}
