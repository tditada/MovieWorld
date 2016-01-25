package ar.edu.itba.paw.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ImageWrapper implements Serializable{

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
