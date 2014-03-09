package ServerClient;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import Model.Model;

import com.google.gson.Gson;

public class Server implements Runnable
{
  static final int PORT = 8080; //default port
  
  

  //instance variables
  Socket connect;

  //constructor
  public Server(Socket connect)
  {
    this.connect = connect;
  }

  /**
   * main method creates a new HttpServer instance for each
   * request and starts it running in a separate thread.
   */
  public static void main(String[] args)
  {
    try
    {
      ServerSocket serverConnect = new ServerSocket(PORT);
      System.out.println("\nListening for connections on port " + PORT + "...\n");
      while (true) //listen until user halts execution
      {
        Server server = new Server(serverConnect.accept()); //instantiate HttpServer

        //create new thread
        Thread threadRunner = new Thread(server);
        threadRunner.start(); //start thread
      }
    }
    catch (IOException e)
    {
      System.err.println("Server error: " + e);
    }
  }


  /**
   * run method services each request in a separate thread.
   */
  public void run()
  {
	  
	processRequest();
    BufferedReader in = null;
    PrintWriter out = null;
    DataOutputStream dataOut = null;

    try
    {
      //get character input stream from client
      in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
      //get character output stream to client (for headers)
      out = new PrintWriter(connect.getOutputStream());
      //get binary output stream to client (for requested data)
      dataOut = new DataOutputStream(connect.getOutputStream());
      
      String currentLine = in.readLine();
      String headerLine = currentLine;        
      StringTokenizer tokenizer = new StringTokenizer(headerLine);
      String httpMethod = tokenizer.nextToken();
      String httpQueryString = tokenizer.nextToken();

      String responseString = "";
      if (httpMethod.equals("GET")) {
   	    System.out.println("GET request");
		// The test home page
		responseString = 
				"<html><body>" +
						"<form action=\"http://127.0.0.1:8080\" enctype=\"multipart/form-data\" method=\"post\">" +
							"Enter the name <input name=\"file\" type=\"text\"><br>" +
							"<input value=\"Submit\" type=\"submit\"></form>"
			   + "</body></html>";           
      }
      else{//POST
    	  System.out.println("POST request");

      }
      
      //send HTTP headers
      dataOut.writeBytes("HTTP/1.0 200 OK"+"\r\n");
      dataOut.writeBytes("Content-Type: text/html" + "\r\n");
      dataOut.writeBytes("Content-length: "+responseString.length()+"\r\n\r\n");

      System.out.println(responseString);
      dataOut.writeBytes(responseString);
      dataOut.flush(); //flush binary output stream buffer
      
    }
    catch (IOException ioe)
    {
      System.err.println("Server Error: " + ioe);
    }
    finally
    {
    	try {
			in.close();
		    out.close();
		    dataOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  }
  
  public void processRequest(){
	  JsonMessage j = new JsonMessage("ariel", new Model());
	  ServerCommand c = new ServerCommand("initGame", true);
	  j.addCommand(c);
	  
	  Gson gson = new Gson();
	  String json = gson.toJson(j);
	  
	  
	  JsonMessage j1 = gson.fromJson(json, JsonMessage.class);
	  
	  Vector<ServerCommand> commands = j1.getCommands();
	  for(int i = 0; i < commands.size(); i++)
		  System.out.println(commands.get(i).type_command+"   "+commands.get(i).content);
	  
  }
}