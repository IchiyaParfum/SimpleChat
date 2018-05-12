package communication;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import communication.messages.BroadcastMessage;
import entity.User;

public class BroadcastServer extends Server{
	
	public BroadcastServer() throws SecurityException, IOException {
		super();
		socket = new DatagramSocket(broadcastPort);
		socket.setBroadcast(true);	//May be required by some OS
	}
	
	public void send(User myUser) {
		send(new BroadcastMessage(myUser), broadcastPort, broadcastIP);
	}
}
