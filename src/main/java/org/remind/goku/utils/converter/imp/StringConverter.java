package org.remind.goku.utils.converter.imp;

import org.remind.goku.utils.converter.Converter;

/**
 * 转换成string类型
 * @author remind
 *
 */
public class StringConverter implements Converter<String>{

	@Override
	public String convert(String s) {
		return s;
	}

}
