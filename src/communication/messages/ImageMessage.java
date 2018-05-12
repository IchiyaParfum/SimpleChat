package communication.messages;

import java.awt.image.BufferedImage;

import entity.User;

public class ImageMessage extends Message{
	private BufferedImage image;
	
	public ImageMessage(User sender, BufferedImage image) {
		super(sender);
		setImage(image);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
}
