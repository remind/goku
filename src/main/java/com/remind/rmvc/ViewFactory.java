package com.remind.rmvc;

import com.remind.rmvc.view.View;
import com.remind.rmvc.view.impl.TextView;

/**
 * view工厂
 * @author remind
 *
 */
public class ViewFactory {

	/**
	 * 文本型view
	 * @param content
	 * @return
	 */
	public static View getTextView(String content) {
		return new TextView(content);
	}
}
