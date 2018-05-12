package communication.messages;

import java.io.Serializable;

import entity.User;

public abstract class Message implements Serializable{
	protected User sender;
	
	protected Message(User sender) {
		setSender(sender);
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public User getSender() {
		return sender;
	}
	
	@Override
	public String toString() {
		if(sender == null) {
			return "Message from: Unknown";
		}
		return getClass().getSimpleName() + " from: " + sender.toString();
	}
}
