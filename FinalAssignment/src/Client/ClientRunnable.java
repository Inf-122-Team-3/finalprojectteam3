package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Vector;

import Util.Command;
import Util.NetworkMessage;

public class ClientRunnable {
	static String HOSTNAME = "127.0.0.1";	//localhost	
	static int PORT = 8080;	
	
	int id = -1;
	Socket kkSocket;
	PrintWriter out;
	BufferedReader in;
	
	public ClientRunnable(String username){
		try  {
			kkSocket = new Socket(HOSTNAME, PORT);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
			
			String fromServer;

			Vector<Command> commands = new Vector<>();
			commands.add(new Command("#SIGNIN", username));
			sendCommand(commands);
			
			while ((fromServer = in.readLine()) != null) {
				processMessage(fromServer);
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + HOSTNAME);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + HOSTNAME);
			System.exit(1);
		}
	}
	
	public static void main(String[] args) {
		ClientRunnable client = new ClientRunnable("ariel");
	}
	
	public void sendCommand(Vector<Command> commands){
		NetworkMessage m = new NetworkMessage();
		m.setCommands(commands);
		
		out.println(m.toJson());
	}
	
	
	public void processMessage(String jsonString){
		NetworkMessage msg = new NetworkMessage(jsonString);
		
		System.out.println("Message received from SERVER");
		System.out.println(msg.toString());
	}
}
