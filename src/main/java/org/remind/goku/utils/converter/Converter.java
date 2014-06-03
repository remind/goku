package org.remind.goku.utils.converter;

/**
 * 类型转换
 * @author remind
 *
 * @param <T>
 */
public interface Converter<T> {
	
	/**
	 * 转换
	 * @param s		要转换的值
	 * @return
	 */
	public T convert(String s);
	
}
