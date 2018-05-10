package entity;

import java.io.Serializable;

public class User implements Serializable{
	protected int id;
	protected String name;
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "My name is " + name + " and I have id# " + id;
	}
}
