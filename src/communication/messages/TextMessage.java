package communication.messages;

public class TextMessage extends Message{
	private String text;
	
	public TextMessage() {
		super();
		setText(new String());
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
