package GameViewAdaptor;

import java.util.ArrayList;
import java.util.List;

import Client.Client;
import Game.GameInstance;
import Game.GameObject;
import Model.Message;
import Model.Model;
import Util.Player;
import Util.SimplifiedModel;
import View.GameView;

public class Adaptor {

	private GameView view;
	private GameInstance game;
	
	public Adaptor(SimplifiedModel model, List<Player> listOfPlayers, Client client){
		
		Player p1 = listOfPlayers.get(0);
		Player p2 = listOfPlayers.get(1);
		
		SimplifiedModel m = model;
		String[][] grid = m.grid;

		ModelClickListener l = new ModelClickListener(game, p1, p2, client);
		view = new GameView(grid, null, new ArrayList<Message>(), l, client);
		l.setView(view);
	}

//	public Adaptor(GameFactory f, List<Player> listOfPlayers, Client client){
//
//		Player p1 = listOfPlayers.get(0);
//		Player p2 = listOfPlayers.get(1);
//
//		game = f.createGame(listOfPlayers);
//		Model m = game.getModel();
//		String[][] grid = new String[m.getBoard().getWidth()][m.getBoard().getHeight()];
//		for(int i = 0; i < m.getBoard().getWidth(); i++)
//		{
//			for(int j = 0; j < m.getBoard().getHeight(); j++)
//			{
//				GameObject o = m.getBoard().getObjectAtLocation(i, j);
//				grid[i][j] = (o==null? "" : o.getRepresentation());
//			}
//		}
//		ModelClickListener l = new ModelClickListener(game, p1, p2);
//		view = new GameView(grid, null, m.getMessages(p1), l);
//		l.setView(view);
//	}
//
//	public Adaptor(GameFactory f){
//		List<Player> players = new ArrayList<Player>();
//		Player p1 = new Player(1);
//		p1.setUsername("Player1");
//		Player p2 = new Player(2);
//		p2.setUsername("Player2");
//		players.add(p1);
//		players.add(p2);
//		game = f.createGame(players);
//		Model m = game.getModel();
//		String[][] grid = new String[m.getBoard().getWidth()][m.getBoard().getHeight()];
//		for(int i = 0; i < m.getBoard().getWidth(); i++)
//		{
//			for(int j = 0; j < m.getBoard().getHeight(); j++)
//			{
//				GameObject o = m.getBoard().getObjectAtLocation(i, j);
//				grid[i][j] = (o==null? "" : o.getRepresentation());
//			}
//		}
//		ModelClickListener l = new ModelClickListener(game, p1, p2);
//		view = new GameView(grid, null, m.getMessages(p1), l);
//		l.setView(view);
//	}
//	
//	
//	public static void main(String[] args){
//		new Adaptor(new Connect4Factory());
//	}
	
}
