package com.shallop.bpc.collection.basic.java;

import org.junit.Test;

import java.text.SimpleDateFormat;
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

}
