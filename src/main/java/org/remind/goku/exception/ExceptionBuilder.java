package org.remind.goku.exception;

public class ExceptionBuilder {

	public static AppException build(String message, Exception e) {
		System.out.println(message);
		e.printStackTrace();
		return new AppException(message, e);
	}
}
