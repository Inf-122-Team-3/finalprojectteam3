package Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import Util.Command;
import Util.NetworkMessage;

public class ClientRunnable {
	static final String URL_SERVER = "http://127.0.0.1:8080";
	static final int PORT = 8080; //default port
	
	public static void main(String[] args) {
		String hostName = "0.0.0.0";	//localhost	
		int portNumber = 8080;			//default port

		try {
			sendPost();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try (
				Socket kkSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
				) {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;
			String fromUser;

			while ((fromServer = in.readLine()) != null) {
				System.out.println("Server: " + fromServer);
				out.println("Good game");
				if (fromServer.equals("Bye."))
					break;

				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
					hostName);
			System.exit(1);
		}*/
	}
	
	// HTTP POST request
	static void sendPost() throws Exception {
 		URL obj = new URL(URL_SERVER);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Connection", "close");

		NetworkMessage m = new NetworkMessage();
		Command c = new Command("move", "a1,a2");
		m.addCommand(c);
		
		String urlParameters = m.toJson();
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.writeBytes("\r\n\r\n");
		wr.flush();
		wr.close();
				
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + URL_SERVER);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while (in.ready()) {
			inputLine = in.readLine();
			response.append(inputLine);
		}
		in.close();
 
		//con.disconnect();
		System.out.println(response.toString());
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
