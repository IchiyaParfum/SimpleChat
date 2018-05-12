package entity;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class User implements Serializable{
	protected String name;
	protected String host;
	protected int port;
	
	public User(String name, String host, int port) {
		setName(name);
		setHost(host);
		setPort(port);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return host;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User u = (User) obj;
			if(u.getName().equals(getName()) 
					&& u.getHost().equals(getHost())
					&& u.getPort() == getPort()) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public String toString() {
		return  getName() + " " + getHost() + ":" + getPort();
	}
}
