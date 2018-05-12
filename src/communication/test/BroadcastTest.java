package communication.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import communication.BroadcastServer;
import communication.ServerObserver;
import communication.messages.BroadcastMessage;
import communication.messages.Message;
import entity.Model;
import entity.test.ModelTest;

public class BroadcastTest implements ServerObserver{
	private BroadcastServer broadcast;
	private Timer clock;
	private Model data;
	
	public static void main(String[] args) {
		BroadcastTest t = new BroadcastTest();
	}
	
	public BroadcastTest() {
			try {
				broadcast = new BroadcastServer();
			} catch (SecurityException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			broadcast.addObserver(this);			
			new Thread(broadcast).start();
			
			clock = new Timer(2000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					broadcast.send(null);
					
				}				
			});
			clock.start();
	}

	@Override
	public void receive(Message msg) {
		if(msg instanceof BroadcastMessage) {
			BroadcastMessage bmsg = (BroadcastMessage) msg;
		}
	}
	
	
}
