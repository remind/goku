package org.remind.goku.utils.converter.imp;

import org.remind.goku.utils.converter.Converter;

/**
 * 转换成int类型
 * @author remind
 *
 */
public class IntegerConverter implements Converter<Integer>{

	@Override
	public Integer convert(String s) {
		return Integer.valueOf(s);
	}

}
