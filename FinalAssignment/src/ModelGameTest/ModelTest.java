package ModelGameTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
		Player p1 = new Player();
		p1.setUsername("test1");
		Player p2 = new Player();
		p2.setUsername("test2");
		List<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		GameInstance i = f.createGame(players);
		printModel(i.getModel());
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		Player p = p1;
		try{
			for(;;)
			{
				String s = r.readLine();
				String[] commands = s.split(" ");
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
		for(Message message : m.getMessages())
		{
			System.out.println(message.getContent());
		}
		m.clearMessages();
	}

}
