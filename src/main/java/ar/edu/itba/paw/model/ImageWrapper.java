package ar.edu.itba.paw.model;

import java.io.Serializable;

import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ImageWrapper implements Serializable {

	public static final int MAX_SIZE = 400 * 1024;

	private byte[] image;

	public ImageWrapper() {
	}

	public ImageWrapper(byte[] image) {
		checkArgument(image, notNull());
		checkArgument(image.length <= MAX_SIZE);
		this.image = image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}

}
