package org.remind.goku.exception;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AppException(String message, Exception e) {
		super(message, e);
	}
}
