package org.remind.goku.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 对html进行处理
 * 
 * @author remind
 *
 */
public class HtmlUtil {
	public static final Map<String, String> replaceChars = new HashMap<String, String>();
	
	static {
		replaceChars.put("&", "&amp;");
		replaceChars.put("<", "&lt;");
		replaceChars.put(">", "&gt;");
	}

	/**
	 * 反转义
	 * @param content
	 * @return
	 */
	public static String unescapeHtml(String content) {
		if (content == null || content.equals("")) {
			return "";
		}
		String value = content;
		for (String key : replaceChars.keySet()) {
			value = StringUtils.replace(value, replaceChars.get(key), key);
		}
		return value;
	}
	
	/**
	 * 转义html
	 * @param content
	 * @return
	 */
	public static String escapeHtml(String content) {
		if (content == null || content.equals("")) {
			return "";
		}
		String value = content;
		for (String key : replaceChars.keySet()) {
			value = StringUtils.replace(value, key, replaceChars.get(key));
		}
		return value;
	}

}
