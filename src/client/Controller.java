package client;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JOptionPane;

import communication.BroadcastServer;
import communication.ClientServer;
import communication.ServerObserver;
import communication.messages.BroadcastMessage;
import communication.messages.ImageMessage;
import communication.messages.Message;
import communication.messages.MessageDecoder;
import communication.messages.RegisterMessage;
import communication.messages.TextMessage;
import communication.messages.UnregisterMessage;
import entity.Model;
import entity.User;
import presentation.Gui;
import presentation.View;
import presentation.ViewObserver;

public abstract class Controller implements ServerObserver, ViewObserver, MessageDecoder{
	protected BroadcastServer broadcastServer;
	protected Thread tBroadcastServer;
	protected ClientServer clientServer;
	protected Thread tClientServer;
	
	protected View view;
	protected Model data;
	
	protected Controller() {
		try {
			broadcastServer = new BroadcastServer();
			broadcastServer.addObserver(this);
		} catch (SocketException e) {
			// TODO Logger
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clientServer = new ClientServer();
			clientServer.addObserver(this);
		} catch (SocketException e) {
			// TODO Logger
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data = new Model();
		view = new Gui(data);
		view.addObserver(this);
		initialization();
		
		//Start servers
		tBroadcastServer = new Thread(broadcastServer);
		tBroadcastServer.start();
		tClientServer = new Thread(clientServer);
		tClientServer.start();
		
		//Send broadcast
		broadcastServer.send(data.getMyUser());
		
	}
	
	protected void initialization() {
		//Show user dialog
		String name = (String)JOptionPane.showInputDialog(
				view.getWindow(),
				"Please enter a user name",
				"",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"John Doe");
		
		data.setMyUser(new User(name, null, clientServer.getPort()));	
	}
	
	@Override
	public void receive(Message msg) {
		//Let message decode for further use
		MessageDecoder.decodeMessage(msg, this);		
	}
	
	@Override
	public void msgReceived(BroadcastMessage msg) {
		//Add user in list
		registerUser(msg.getSender());
		view.updateUsers();
		
		//Send my data to user => not using broadcasting anymore
		RegisterMessage rmsg = new RegisterMessage(data.getMyUser());
		clientServer.send(rmsg, msg.getSender().getPort(), msg.getSender().getHost());
	}

	@Override
	public void msgReceived(TextMessage msg) {
		//Add message to chat
		data.getChat().add(msg);
		view.updateChat();
	}

	@Override
	public void msgReceived(ImageMessage msg) {
		//Add message to chat
		data.getChat().add(msg);
		view.updateChat();
	}
	
	@Override
	public void msgReceived(RegisterMessage msg) {
		//Add user in list
		registerUser(msg.getSender());
		view.updateUsers();
		
		//Send my data to user => not using broadcasting anymore
		RegisterMessage rmsg = new RegisterMessage(data.getMyUser());
		clientServer.send(rmsg, msg.getSender().getPort(), msg.getSender().getHost());
		
	}

	@Override
	public void msgReceived(UnregisterMessage msg) {
		unregisterUser(msg.getSender());
		view.updateUsers();
	}

	@Override
	public void closeWindowClicked(WindowEvent e) {
		//Send unregister message to all users
		UnregisterMessage umsg = new UnregisterMessage(data.getMyUser());
		sendMessages(umsg);
		
		//Stop server threads and release sockets
		broadcastServer.stopServer();
		clientServer.stopServer();
		boolean f = tBroadcastServer.isAlive();
		
		try {
			//Wait until threads died
			tBroadcastServer.join(1000);
			tClientServer.join(1000);
		} catch (InterruptedException e1) {
			//Exit application regardless of exceptions
		}
		
		//Exit application
		System.exit(0);
	}

	@Override
	public void saveChatClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageClicked(String text, String attach) {
		//Send messages to users TODO Picture messages
		TextMessage tmsg = new TextMessage(data.getMyUser(), text);
		sendMessages(tmsg);
		
		//Publish own message
		data.getChat().add(tmsg);
		view.updateChat();
		
	}

	protected boolean registerUser(User u) {
		//Add user to list
		if(data.getUsers().containsKey(u.getName())) {
			//Remove existing user if address has changed
			if(!data.getUsers().get(u.getName()).equals(u)) {
				data.getUsers().remove(u.getName());
				data.getUsers().put(u.getName(), u);
				return true;
			}
		}else {
			data.getUsers().put(u.getName(), u);
			return true;
		}
		return false;
	}
	
	protected boolean unregisterUser(User u) {
		//Remove user from list
		if(data.getUsers().containsKey(u.getName())) {
			//Remove existing user
			data.getUsers().remove(u.getName());
			return true;
		}
		return false;
	}
	
	protected void sendMessages(Message msg) {
		for(User u : data.getUsers().values()) {
			clientServer.send(msg, u.getPort(), u.getHost());
		}
	}
	
}
