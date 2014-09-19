package ar.edu.itba.paw.g4.exception;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {

//	public ServiceException(Exception e) {
//		super(e.getMessage(), e);
//	}
//	
	public ServiceException(String message) {
		super(message);
	}
	
}
