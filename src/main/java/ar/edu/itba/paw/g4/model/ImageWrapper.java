package ar.edu.itba.paw.g4.model;

import javax.persistence.Embeddable;

@Embeddable
public class ImageWrapper {

	private byte[] image;
	
	public ImageWrapper() {
	}
	
	public ImageWrapper(byte[] image) {
		this.image = image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public byte[] getImage() {
		return image;
	}

}
