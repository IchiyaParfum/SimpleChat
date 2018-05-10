package communication;

import communication.messages.Message;

public interface ServerObserver extends Observer{
	public void receive(Message msg);
}
