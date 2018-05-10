package communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.Timer;

import communication.messages.BroadcastMessage;
import entity.Model;

public class BroadcastServer extends Server{
	private Model data;
	private Timer clock;
	
	public BroadcastServer(Model data) throws SocketException {
		super();
		socket = new DatagramSocket(broadcastPort);
		socket.setBroadcast(true);
		
		this.data = data;
		initialize();
	}
	
	private void initialize() {
		clock = new Timer(broadcastInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Sends a broadcast message after timer reaches its time
				try {
					send(new BroadcastMessage(data.getMyUser()), broadcastPort, InetAddress.getByName(broadcastIP));
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});	
		clock.start();
	}
}
