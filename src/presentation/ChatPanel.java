package presentation;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

public class ChatPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4304403924411783670L;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	public ChatPanel() {
		initializeComponents();
	}
	
	private void initializeComponents() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		scrollPane = new JScrollPane(textArea);
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}

	public void publisch(String text) {		
		//Appends text to text area. Does nothing if text is null
		textArea.append(text + "\n");
		scrollToBottom();
	}
	public void scrollToBottom() {
		//Sets viewport position to the bottom of text area
		JViewport vp = scrollPane.getViewport();
		vp.setViewPosition(new Point(0, vp.getViewSize().height)); 
	}
}
