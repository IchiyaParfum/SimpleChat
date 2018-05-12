package communication.messages;

import entity.User;

public class UnregisterMessage extends Message{

	public UnregisterMessage(User sender) {
		super(sender);
	}

}
