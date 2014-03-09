package ModelGameTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Game.GameFactory;
import Game.GameInstance;
import Game.GameObject;
import Game.TicTacToe.TicTacToeFactory;
import Model.Message;
import Model.Model;
import Util.Player;

public class ModelTest {
	
	public static void main(String[] args){
		GameFactory f = new TicTacToeFactory();
		Player p1 = new Player(0);
		p1.setUsername("test1");
		Player p2 = new Player(1);
		p2.setUsername("test2");
		List<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		GameInstance i = f.createGame(players);
		for(Player p : players)
			System.out.println("Player - " + p.getUsername() + " has entered");
		printModel(i.getModel());
		System.out.println("simulation start, type \"help\" for more information on commands");
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		Player p = p1;
		try{
			for(;;)
			{
				String s = r.readLine();
				String[] commands = s.split(" ");
				try{
					if(commands[0].equals("exit"))
					{
						break;
					}
					else if(commands[0].equals("click"))
					{
						int x = Integer.parseInt(commands[1]);
						int y = Integer.parseInt(commands[2]);
						printModel(i.update(x, y, p));
					}
					else if(commands[0].equals("player"))
					{
						int num = Integer.parseInt(commands[1]);
						p = players.get(num);
						System.out.println("swapped to player - " + p.getUsername());
					}
					else if(commands[0].equals("help"))
					{
						System.out.println("type \"click [x] [y]\" to perform a \"click\" on location [x] [y]");
						System.out.println("type \"player [num]\" to swap to player number [num]");
						System.out.println("type \"exit\" to exit the simulation");
					}
				}
				catch(Exception e){
					System.out.println("error processing command");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
	}
	
	private static void printModel(Model m){
		for(int i = 0; i < m.getBoard().getHeight(); i++)
		{
			for(int j = 0; j < m.getBoard().getWidth(); j++)
			{
				GameObject o = m.getBoard().getObjectAtLocation(j, i);
				System.out.print((o==null ? "_" : o.getRepresentation()) + " ");
			}
			System.out.println();
		}
		for(Entry<Player, List<Message>> e : m.getAllMessages().entrySet())
		{
			for(Message message : e.getValue())
				System.out.println(e.getKey().getUsername() + " : " + message.getContent());
		}
		m.clearMessages();
	}

}
