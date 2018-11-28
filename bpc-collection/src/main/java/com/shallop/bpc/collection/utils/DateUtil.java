package com.shallop.bpc.collection.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenxuanlong
 * @date 2016/1/6
 */
public class DateUtil {

	public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String formatDate(Date date) {
		return formatDate(date, PATTERN);
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null) throw new IllegalArgumentException("date is null");
		if (pattern == null) throw new IllegalArgumentException("pattern is null");

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
}
