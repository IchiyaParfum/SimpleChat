package communication;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientServer extends Server{

	public ClientServer() throws SocketException {
		super();
		super.socket = new DatagramSocket();
	}

}
