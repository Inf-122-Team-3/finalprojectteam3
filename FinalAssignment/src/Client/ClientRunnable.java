package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Vector;

import Util.Command;
import Util.NetworkMessage;

public class ClientRunnable {
	static final String URL_SERVER = "http://127.0.0.1:8080";
	static final int PORT = 8080; //default port
	
	public static void main(String[] args) {
		String hostName = "0.0.0.0";	//localhost	
		int portNumber = 8080;			//default port

		try {
			Vector<Command> commands = new Vector<>();
			commands.add(new Command("test", "test_content"));
			sendCommand(commands);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// HTTP POST request
	static void sendCommand(Vector<Command> commands) throws Exception {
 		URL obj = new URL(URL_SERVER);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Connection", "close");

		NetworkMessage m = new NetworkMessage();
		m.setCommands(commands);
		
		String urlParameters = m.toJson();
 
		// Send post request
		con.setDoOutput(true);
		con.connect();
		
		PrintWriter out = new PrintWriter(con.getOutputStream(), true);
		out.println(urlParameters+"\r\n\r\nEOF");		
				
		int responseCode = con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while (in.ready()) {
			inputLine = in.readLine();
			response.append(inputLine);
		}
		
		in.close();
		out.close();
		con.disconnect();
	}
	
	public void initSocket(){
		try {
			ServerSocket clientConnect = new ServerSocket(PORT);
			System.out.println("\nListening for connections on port " + PORT + "...\n");
			//listen until user halts execution
			while (true) {
				Socket serverSocket = clientConnect.accept();
				//create new thread
				
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(serverSocket.getInputStream()));
				
				processMessage(new NetworkMessage(in.readLine()));
				//Thread threadRunner = new Thread(server);
				//threadRunner.start(); //start thread
			}
		}
		catch (IOException e) {
			System.err.println("Server error: " + e);
		}
	}
	
	public void processMessage(NetworkMessage m){
		
	}
}
