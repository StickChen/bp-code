package com.shallop.bpc.collection.basic.java;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author StickChen
 * @date 2017/10/5
 */
public class DateDemo {

    @Test
    public void testDateDemo(){
        System.out.println(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
    }

    @Test
    public void test1(){
        Date date = new Date();
        Date dayEndTime = getDayEndTime(date);
        System.out.println(dayEndTime);
    }

    private Date getDayEndTime(Date limitPayDate) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(limitPayDate);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        instance.set(Calendar.MILLISECOND, 999);
        return instance.getTime();
    }
}
