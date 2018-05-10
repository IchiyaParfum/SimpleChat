package communication.messages;

import java.net.DatagramSocket;

import entity.User;

public class BroadcastMessage extends Message{
	private User user;
	
	public BroadcastMessage(User user) {
		super();
		setUser(user);
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return super.toString() + " User: " + user.toString();
	}
	
}
