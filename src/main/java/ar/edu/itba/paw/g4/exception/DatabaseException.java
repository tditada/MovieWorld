package ar.edu.itba.paw.g4.exception;

@SuppressWarnings("serial")
public class DatabaseException extends RuntimeException {

	public DatabaseException(Exception e) {
		super(e.getMessage(), e);
	}

}