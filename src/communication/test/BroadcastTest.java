package communication.test;

import java.net.SocketException;

import communication.BroadcastServer;
import communication.ServerObserver;
import communication.messages.BroadcastMessage;
import communication.messages.Message;
import entity.test.ModelTest;

public class BroadcastTest implements ServerObserver{
	private BroadcastServer broadcast;
	
	public static void main(String[] args) {
		BroadcastTest t = new BroadcastTest();
	}
	
	public BroadcastTest() {
		try {
			broadcast = new BroadcastServer(new ModelTest());
			
			broadcast.addObserver(this);
			
			new Thread(broadcast).start();
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void receive(Message msg) {
		if(msg instanceof BroadcastMessage) {
			BroadcastMessage bmsg = (BroadcastMessage) msg;
			System.out.println(bmsg.toString());
		}
	}
	
	
}
