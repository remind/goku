package org.remind.goku.exception;

/**
 * 异常builder类
 * @author remind
 *
 */
public class ExceptionBuilder {

	/**
	 * 返回一个异常处理类实例
	 * @param message	要显示的消息
	 * @param e		exception
	 * @return
	 */
	public static GokuException build(String message, Exception e) {
		System.out.println(message);
		e.printStackTrace();
		return new GokuException(message, e);
	}
}
