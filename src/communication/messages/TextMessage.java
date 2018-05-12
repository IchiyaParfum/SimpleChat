package communication.messages;

import entity.User;

public class TextMessage extends Message{
	private String text;
	
	public TextMessage(User sender, String text) {
		super(sender);
		setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Text: " + text;
	}
	
}
