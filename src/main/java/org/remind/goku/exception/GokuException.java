package org.remind.goku.exception;

/**
 * goku中runtime类型的异常处理
 * @author remind
 */
public class GokuException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GokuException(String message, Exception e) {
		super(message, e);
	}
}
