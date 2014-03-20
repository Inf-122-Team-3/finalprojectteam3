package Game.Chess;

import java.util.ArrayList;
import java.util.List;

import Game.GameInstance;
import Model.Board;
import Model.Message;
import Model.Model;
import Util.Player;

public class ChessInstance extends GameInstance{
	
	private List<KingObject> kings;
	private ChessObject selectedObject;
	private int turn;
	private List<Boolean> checks;

	public ChessInstance(List<Player> players) {
		super(players);
	}

	@Override
	protected Model createModel() {
		Model m = new Model(new Board(8, 8));
		kings = new ArrayList<KingObject>();
		checks = new ArrayList<Boolean>();
		//make the Pawns
		for(int i = 0; i < m.getBoard().getWidth(); i++)
		{
			m.getBoard().setObjectAtLocation(i, 1, new PawnObject(m.getBoard(), new Location(i, 1), "B", 1));
			m.getBoard().setObjectAtLocation(i, 6, new PawnObject(m.getBoard(), new Location(i, 6), "W", -1));
		}
		//make the Rooks
		m.getBoard().setObjectAtLocation(0, 0, new RookObject(m.getBoard(), new Location(0, 0), "B"));
		m.getBoard().setObjectAtLocation(7, 0, new RookObject(m.getBoard(), new Location(7, 0), "B"));
		m.getBoard().setObjectAtLocation(0, 7, new RookObject(m.getBoard(), new Location(0, 7), "W"));
		m.getBoard().setObjectAtLocation(7, 7, new RookObject(m.getBoard(), new Location(7, 7), "W"));
		//make the Horses
		m.getBoard().setObjectAtLocation(1, 0, new KnightObject(m.getBoard(), new Location(1, 0), "B"));
		m.getBoard().setObjectAtLocation(6, 0, new KnightObject(m.getBoard(), new Location(6, 0), "B"));
		m.getBoard().setObjectAtLocation(1, 7, new KnightObject(m.getBoard(), new Location(1, 7), "W"));
		m.getBoard().setObjectAtLocation(6, 7, new KnightObject(m.getBoard(), new Location(6, 7), "W"));
		//make the Bishops
		m.getBoard().setObjectAtLocation(2, 0, new BishopObject(m.getBoard(), new Location(2, 0), "B"));
		m.getBoard().setObjectAtLocation(5, 0, new BishopObject(m.getBoard(), new Location(5, 0), "B"));
		m.getBoard().setObjectAtLocation(2, 7, new BishopObject(m.getBoard(), new Location(2, 7), "W"));
		m.getBoard().setObjectAtLocation(5, 7, new BishopObject(m.getBoard(), new Location(5, 7), "W"));
		//make the Queens
		m.getBoard().setObjectAtLocation(3, 0, new QueenObject(m.getBoard(), new Location(3, 0), "B"));
		m.getBoard().setObjectAtLocation(3, 7, new QueenObject(m.getBoard(), new Location(3, 7), "W"));
		//make the Kings
		KingObject whiteKing = new KingObject(m.getBoard(), new Location(4, 7), "W", getPlayers().get(0));
		KingObject blackKing = new KingObject(m.getBoard(), new Location(4, 0), "B", getPlayers().get(1));
		m.getBoard().setObjectAtLocation(4, 7, whiteKing);
		m.getBoard().setObjectAtLocation(4, 0, blackKing);
		kings.add(whiteKing);
		kings.add(blackKing);
		checks.add(false);
		checks.add(false);
		return m;
	}
	
	/**
	 * checks to see if it is the given Player's turn
	 * @param p the Player to check against
	 * @return whether or not it is that Player's turn
	 */
	private boolean isPlayersTurn(Player p){
		return !isGameOver()&&p.equals(getPlayers().get(turn));
	}

	@Override
	public void onPlayerClick(int x, int y, Player p) {
		getModel().clearMessages();
		if(!isPlayersTurn(p))
		{
			if(isGameOver())
				getModel().addMessage(new Message("the game is over!"), p);
			else
				getModel().addMessage(new Message("it's not your turn"), p);
			return;
		}
		Board b = getModel().getBoard();
		if(selectedObject==null)
		{
			System.out.println("here");
			ChessObject o = (ChessObject) b.getObjectAtLocation(x, y);
			if(o!=null&&o.isSameTeam(kings.get(turn)))
			{
				if(checks.get(turn)&&!(o instanceof KingObject))
				{
					getModel().addMessage(new Message("you are in check!"), p);
				}
				else
				{
					selectedObject = o;
					o.setSelected(true);
				}
			}
		}
		else
		{
			Location loc = new Location(x, y);
			if(selectedObject.getValidMoves().contains(loc))
			{
				selectedObject.move(loc);
				selectedObject.setSelected(false);
				selectedObject = null;
				checks.set(turn, false);
				for(KingObject o : kings)
				{
					if(o.isThreatened())
					{
						List<Location> kingsMoves = o.getValidMoves();
						if(allMovesThreatened(kingsMoves, o))
						{
							alertCheckmate(o.getPlayer());
						}
						else
						{
							getModel().addMessage(new Message("check!"), p);
							checks.set(kings.indexOf(o), true);
						}
					}
					else if(countValidMoves(getPlayers().get(turn))==0)
					{
						//stalemate
						for(Player play : getPlayers())
						{
							alertLoss(play);
						}
					}
				}
				if(++turn>=getPlayers().size())
					turn = 0;
			}
			else
			{
				getModel().addMessage(new Message("cannot move there!"), p);
				selectedObject.setSelected(false);
				selectedObject = null;
			}
		}
	}
	
	private int countValidMoves(Player p)
	{
		int index = getPlayers().indexOf(p);
		KingObject o = kings.get(index);
		int sumMoves = 0;
		for(int i = 0; i < getModel().getBoard().getWidth(); i++)
		{
			for(int j = 0; j < getModel().getBoard().getHeight(); j++)
			{
				ChessObject co = (ChessObject) getModel().getBoard().getObjectAtLocation(i, j);
				if(co!=null&&co.isSameTeam(o))
					sumMoves+=co.getValidMoves().size();
			}
		}
		return sumMoves;
	}
	
	private boolean allMovesThreatened(List<Location> moves, KingObject king)
	{
		for(Location l : moves)
		{
			if(!king.isLocationThreatened(l))
				return false;
		}
		return true;
	}
	
	private void alertCheckmate(Player p)
	{
		//checkmate
		alertLoss(p);
		for(Player play : getPlayers())
		{
			if(!play.equals(p))
				alertWin(play);
		}
		setGameOver();
	}

	@Override
	public void onPlayerLeave(Player p) {
		getPlayers().remove(p);
		if(getPlayers().size()==0||isGameOver())
			return;
		alertLoss(p);
		if(getPlayers().size()==1)
			alertWin(getPlayers().get(0));
	}

}
