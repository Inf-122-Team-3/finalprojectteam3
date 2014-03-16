package Util;

public class Invite {
	private int inviteId;
	private String senderUsername;
	private String recipientUserName;
	private String game;
	private boolean accepted;
	
	public Invite(String senderUsername, String recipientUserName, String game)
	{
		this.inviteId = IDGenerator.getNextID();
		this.recipientUserName = recipientUserName;
		this.senderUsername = senderUsername;
		this.game = game;
		accepted = false;
	}
	
	public int getInviteId() {
		return inviteId;
	}
	
	public String getSenderUsername() {
		return senderUsername;
	}

	public String getRecipientUsername() {
		return recipientUserName;
	}
	

	public String getGame() {
		return game;
	}
	
	public boolean getAccepted() {
		return accepted;
	}
	
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
