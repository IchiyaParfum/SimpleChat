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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import communication.messages.Message;
import entity.User;

public abstract class Server implements Runnable, ServerConstants, Subject{
	protected DatagramSocket socket;
	protected boolean running = false;
	protected byte[] buffer;
	protected Logger logger;
	protected List<ServerObserver> observers;
	
    public Server() throws SecurityException, IOException {
        socket = new DatagramSocket(0);	//Choose any available port
        buffer = new byte[bufferSize];              
        observers = new ArrayList<>();
        
        //Set up logger
        logger = Logger.getLogger(this.getClass().getPackage().getName());
        FileHandler fh = new FileHandler("server.log");	//Write log file in parent folder
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
    
    public void stopServer() {
    	running = false;
    }
    
	public boolean isRunning() {
		return running;
	}
    
	@Override
	public void run() {
		running = true;
		 
        while (running) {         
            try {
            	//Receive message
				Message message = receive();
				logger.info(message.toString());
				//Inform observers
				for(ServerObserver so : observers) {
					so.receive(message);
				}

			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
        }
        socket.close();
		
	}

	public Message receive() throws IOException {
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		
		try {
			//Try to deserialize data into message object
			ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
			Message message = (Message) iStream.readObject();
			iStream.close();
			//Set address from packet when receiving message
			message.setSender(new User(message.getSender().getName(), 
					packet.getAddress().getHostAddress(), 
					message.getSender().getPort()));
			return message;
		}catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}catch(ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}		
	}
	
    public void send(Message msg, int port, String host) {
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
				packet = new DatagramPacket(serializedMessage, serializedMessage.length, InetAddress.getByName(host), port);
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
    
    public int getPort() {
    	return socket.getLocalPort();
    }
}

