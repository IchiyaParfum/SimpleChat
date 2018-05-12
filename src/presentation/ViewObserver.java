package presentation;

import java.awt.event.WindowEvent;

import communication.Observer;

public interface ViewObserver extends Observer{
	public void closeWindowClicked(WindowEvent e);
	public void saveChatClicked();
	public void sendMessageClicked(String text, String attach);
	
}
