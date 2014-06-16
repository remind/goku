package org.remind.goku.utils.converter;

import java.util.HashMap;
import java.util.Map;

import org.remind.goku.utils.converter.imp.IntegerConverter;
import org.remind.goku.utils.converter.imp.StringConverter;

/**
 * 类型转换工厂
 * @author remind
 *
 */
public class ConverterFactory {
	
	private static Map<Class<?>, Converter<?>> converterMap = new HashMap<Class<?>, Converter<?>>();
	
	static {
		converterMap.put(Integer.class, new IntegerConverter());
		converterMap.put(int.class, new IntegerConverter());
		converterMap.put(String.class, new StringConverter());
	}

	/**
	 * 获取转换实例类
	 * @param clazz
	 * @return
	 */
	public static Converter<?> getConverter(Class<?> clazz) {
		return converterMap.get(clazz);
	}
	
	/**
	 * 添加converter
	 * @param clazz		转换的目标类型
	 * @param converter		转换器
	 */
	public static void addConverter(Class<?> clazz, Converter<?> converter) {
		converterMap.put(clazz, converter);
	}
}
