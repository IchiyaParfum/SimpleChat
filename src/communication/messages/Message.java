package communication.messages;

import java.io.Serializable;

public abstract class Message implements Serializable{
	protected int id;
	
	@Override
	public String toString() {
		return "ID: " + id;
	}
}
