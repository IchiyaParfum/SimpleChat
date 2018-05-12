package communication.messages;


public interface MessageDecoder {
	//Decoder
	public static void decodeMessage(Message msg, MessageDecoder decoder) {
		if(msg == null) {
			//do nothing
		}
		if(msg instanceof BroadcastMessage)
			decoder.msgReceived((BroadcastMessage)msg);
		if(msg instanceof TextMessage)
			decoder.msgReceived((TextMessage)msg);
		if(msg instanceof ImageMessage) {
			decoder.msgReceived((ImageMessage) msg);
		}
		if(msg instanceof RegisterMessage) {
			decoder.msgReceived((RegisterMessage) msg);
		}
		if(msg instanceof UnregisterMessage) {
			decoder.msgReceived((UnregisterMessage) msg);
		}
	}
		
	//Handler methods
	void msgReceived(BroadcastMessage msg);
	void msgReceived(TextMessage msg);
	void msgReceived(ImageMessage msg);
	void msgReceived(RegisterMessage msg);
	void msgReceived(UnregisterMessage msg);
}
