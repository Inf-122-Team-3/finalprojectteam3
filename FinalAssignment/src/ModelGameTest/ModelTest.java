package ModelGameTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Game.GameFactory;
import Game.GameInstance;
import Game.GameObject;
import Game.Connect4.Connect4Factory;
import Game.TicTacToe.TicTacToeFactory;
import Model.Message;
import Model.Model;
import Util.Player;

/**
 * Generic enough test of the Game/Model system. Should be able to test any Game Plugin with a 1 line Change
 *
 */
public class ModelTest {
	
	private GameFactory factory;
	private List<Player> players;
	private boolean verbose;
	
	public ModelTest(GameFactory factory){
		this(factory, System.in, System.out);
	}
	
	public ModelTest(GameFactory factory, InputStream in, PrintStream out)
	{
		this.factory = factory;
		Player p1 = new Player(0);
		p1.setUsername("test1");
		Player p2 = new Player(1);
		p2.setUsername("test2");
		players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
	}
	
	/**
	 * runs this ModelTest, taking input from and printing to the Console
	 */
	public void run(){
		run(System.in, System.out);
	}
	
	/**
	 * runs this ModelTest, given an input stream and a output print stream
	 * @param in the InputStream to read inputFrom
	 * @param out the PrintStream to output to.
	 */
	public void run(InputStream in, PrintStream out){
		GameInstance i = factory.createGame(players);
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		for(Player p : players)
			out.println("Player - " + p.getUsername() + " has entered");
		printModel(i.getModel(), out);
		out.println("simulation start, type \"help\" for more information on commands");
		Player p = players.get(0);
		try{
			for(;;)
			{
				String s = r.readLine();
				String[] commands = s.split(" ");
				try{
					if(commands[0].equals("exit"))
						break;
					else if(commands[0].equals("click"))
						makeMove(i, Integer.parseInt(commands[1]), Integer.parseInt(commands[2]), p, out);
					else if(commands[0].equals("player"))
						p = swapPlayer(players, Integer.parseInt(commands[1]), out);
					else if(commands[0].equals("help"))
						printHelp(out);
					else if(commands[0].equals("verbose"))
						setVerbose(true);
				}
				catch(Exception e){
					if(isVerbose())
						e.printStackTrace();
					else
						out.println("error processing command");
				}
			}
		}
		catch(Exception e){
			if(isVerbose())
				e.printStackTrace();
		}
		finally{
			try{
				r.close();
			}
			catch(Exception e){}
		}
	}
	
	private void printHelp(PrintStream w)
	{
		w.println("type \"click [x] [y]\" to perform a \"click\" on location [x] [y]");
		w.println("type \"player [num]\" to swap to player number [num]");
		w.println("type \"exit\" to exit the simulation");
	}
	
	private void makeMove(GameInstance i, int x, int y, Player p, PrintStream w)
	{
		printModel(i.update(x, y, p), w);
	}
	
	private Player swapPlayer(List<Player> players, int num, PrintStream w)
	{
		Player p = players.get(num);
		w.println("swapped to player - " + p.getUsername());
		return p;
	}
	
	private void printModel(Model m, PrintStream w){
		for(int i = 0; i < m.getBoard().getHeight(); i++)
		{
			for(int j = 0; j < m.getBoard().getWidth(); j++)
			{
				GameObject o = m.getBoard().getObjectAtLocation(j, i);
				w.print((o==null ? "_" : o.getRepresentation()) + " ");
			}
			w.println();
		}
		for(Entry<Player, List<Message>> e : m.getAllMessages().entrySet())
		{
			for(Message message : e.getValue())
				w.println(e.getKey().getUsername() + " : " + message.getContent());
		}
		m.clearMessages();
	}
	
	public static void main(String[] args){
		//this should be the only line that needs to be changed to test other games
		GameFactory f = new Connect4Factory();
		ModelTest t = new ModelTest(f);
		t.run();
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

}
