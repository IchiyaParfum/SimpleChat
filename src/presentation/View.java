package presentation;

import java.awt.Window;

import communication.Subject;

public interface View extends Subject{
	public void updateUsers();
	public void updateChat();
	public Window getWindow();
}
