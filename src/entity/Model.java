package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import communication.messages.Message;

public class Model {
	private User myUser;
	private Map<String, User> users;
	private Map<User, LinkedBlockingQueue<Message>> messageBuffer;
	private List<Message> chat;
	
	public Model() {
		setUsers(new HashMap<>());
		setMessageBuffer(new HashMap<>());
		setChat(new ArrayList<>());
	}

	public User getMyUser() {
		return myUser;
	}

	public void setMyUser(User myUser) {
		this.myUser = myUser;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	public Map<User, LinkedBlockingQueue<Message>> getMessageBuffer() {
		return messageBuffer;
	}

	public void setMessageBuffer(Map<User, LinkedBlockingQueue<Message>> messageBuffer) {
		this.messageBuffer = messageBuffer;
	}

	public List<Message> getChat() {
		return chat;
	}

	public void setChat(List<Message> chat) {
		this.chat = chat;
	}
	
}
