package communication.messages;

import entity.User;

public class RegisterMessage extends Message{

	public RegisterMessage(User sender) {
		super(sender);
	}

}
