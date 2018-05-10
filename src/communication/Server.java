package communication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import communication.messages.Message;

public abstract class Server implements Runnable, ServerConstants, Subject{
	protected DatagramSocket socket;
	protected boolean running = false;
	protected byte[] buffer;
	
	protected List<ServerObserver> observers;
	
    public Server() throws SocketException {
        socket = new DatagramSocket();	//Choose any available port
        buffer = new byte[bufferSize];
        
        observers = new ArrayList<>();
    }
    
    public void stopServer() {
    	running = false;
    }
    
	@Override
	public void run() {
		running = true;
		 
        while (running) {         
            try {
            	//Receive message
				Message message = receive();

				//Inform observers
				for(ServerObserver so : observers) {
					so.receive(message);
				}

			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
        }
		
	}

	public Message receive() throws IOException {
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		
		try {
			//Try to deserialize data into message object
			ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
			Message message = (Message) iStream.readObject();
			iStream.close();
			return message;
		}catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}catch(ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}		
	}
	
    public void send(Message msg, int port, InetAddress address) {
    	try {
			// Serialize message to a byte array
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutput oo = new ObjectOutputStream(bStream); 
			oo.writeObject(msg);
			oo.close();
	
			byte[] serializedMessage = bStream.toByteArray();
			
			try {
				//Send data
		        DatagramPacket packet;
				packet = new DatagramPacket(serializedMessage, serializedMessage.length, address, port);
				socket.send(packet);	
			}catch(UnknownHostException ex) {
				ex.printStackTrace();
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
    };
    
	@Override
    public void addObserver(Observer observer) {
    	//Only add observer if observer is a server observer and not already in list
    	if(observer instanceof ServerObserver && !observers.contains(observers)) {
    		observers.add((ServerObserver) observer);
    	}
    }
    
    @Override
    public boolean removeObserver(Observer observer) {
    	if(observer != null) {
        	return observers.remove(observer);
    	}
    	return false;
    }
    
    @Override
	public List<Observer> getObservers(){
    	List<Observer> list = new ArrayList<>();
    	
    	//Copy server observers into observer list
    	for(ServerObserver so : observers) {
    		list.add(so);
    	}
    	
    	return list;
    }
}

