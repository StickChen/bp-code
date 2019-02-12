package com.shallop.bpc.collection.project.gnote;


import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Calendar 类时间操作
 * @author chenxuanlong
 * @date 2016/2/19
 * 注意事项：
	Calendar 的 month 从 0 开始，也就是全年 12 个月由 0 ~ 11 进行表示。
	而 Calendar.DAY_OF_WEEK 定义和值如下：
	Calendar.SUNDAY = 1
 	……

 	常用格式：
 	yyyy-MM-dd HH:mm:ss:SSS
 */
public class CalendarDemo {

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testSetDate() throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(simpleDateFormat.parse("2016-02-28 23:34:45"));

		System.out.println(simpleDateFormat.format(cal.getTime()));

		cal.set(2016, 1, 28, 23, 34, 46);

		System.out.println(simpleDateFormat.format(cal.getTime()));
	}

	@Test
	public void testGetField(){
		Calendar calendar = Calendar.getInstance();
		// 显示年份
		System.out.println(calendar.get(Calendar.YEAR));

		// 显示月份 (从0开始, 实际显示要加一)
		System.out.println(calendar.get(Calendar.MONTH));

		// 本周几
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("week is = " + week);

		// 今年的第 N 天
		int DAY_OF_YEAR = calendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("DAY_OF_YEAR is = " + DAY_OF_YEAR);

		// 本月第 N 天
		int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("DAY_OF_MONTH = " + String.valueOf(DAY_OF_MONTH));

		// 3小时以后
		calendar.add(Calendar.HOUR_OF_DAY, 3);
		int HOUR_OF_DAY = calendar.get(Calendar.HOUR_OF_DAY);
		System.out.println("HOUR_OF_DAY + 3 = " + HOUR_OF_DAY);

		// 当前分钟数
		int MINUTE = calendar.get(Calendar.MINUTE);
		System.out.println("MINUTE = " + MINUTE);

		// 15 分钟以后
		calendar.add(Calendar.MINUTE, 15);
		MINUTE = calendar.get(Calendar.MINUTE);
		System.out.println("MINUTE + 15 = " + MINUTE);

		// 30分钟前
		calendar.add(Calendar.MINUTE, -30);
		MINUTE = calendar.get(Calendar.MINUTE);
		System.out.println("MINUTE - 30 = " + MINUTE);

		// 格式化显示
		System.out.println(simpleDateFormat.format(calendar.getTime()));

		// 重置 Calendar 显示当前时间
		calendar.setTime(new Date());
		System.out.println(simpleDateFormat.format(calendar.getTime()));

		// 创建一个 Calendar 用于比较时间
		Calendar calendarNew = Calendar.getInstance();

		// 设定为 5 小时以前，后者大，显示 -1
		calendarNew.add(Calendar.HOUR, -5);
		System.out.println("时间比较：" + calendarNew.compareTo(calendar));

		// 设定7小时以后，前者大，显示 1
		calendarNew.add(Calendar.HOUR, +7);
		System.out.println("时间比较：" + calendarNew.compareTo(calendar));

		// 退回 2 小时，时间相同，显示 0
		calendarNew.add(Calendar.HOUR, -2);
		System.out.println("时间比较：" + calendarNew.compareTo(calendar));

	}

	@Test
	public void testDiff() throws ParseException {

		// 得微秒级时间差
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(simpleDateFormat.parse("2016-02-28 23:34:45"));

		Calendar calendarBegin = Calendar.getInstance();
		calendarBegin.setTime(simpleDateFormat.parse("2016-02-24 23:34:00"));
		long val = calendarEnd.getTimeInMillis() - calendarBegin.getTimeInMillis();
		// 换算后得到天数
		long day = val / (1000 * 60 * 60 * 24);
		System.out.println(day);
	}

	@Test
	public void basic(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		String minDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String maxDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
		System.out.println(minDate);
		System.out.println(maxDate);
	}

	@Test
	public void maxDay(){
		Date minDate;
		Date maxDate;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		minDate = cal.getTime();
		cal.add(Calendar.DATE, 1);
//		cal.add(Calendar.WEEK_OF_MONTH, 1);
//		cal.add(Calendar.MONTH, 1);
		maxDate = cal.getTime();

		System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(minDate));
		System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(maxDate));


	}

}
