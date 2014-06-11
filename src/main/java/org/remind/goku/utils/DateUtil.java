package org.remind.goku.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getCurrentByFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d = new Date();
		return sdf.format(d);
	}
}
