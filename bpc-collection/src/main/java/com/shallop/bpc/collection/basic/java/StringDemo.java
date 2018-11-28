package com.shallop.bpc.collection.basic.java;

import org.junit.Test;

import static com.shallop.bpc.collection.utils.Printer.pt;

/**
 * @author StickChen
 * @date 2017/10/4
 */
public class StringDemo {

    @Test
    public void testStringDemo(){
        System.out.println(String.format("%.1f", 0.694));
    }

    @Test
    public void test1(){
        int length = "<Body>".length();
        pt(length);
    }
    
    @Test
    public void test2(){
        pt("" + null);
    }

    @Test
    public void testSB(){
        StringBuffer sb = new StringBuffer("18618396952").replace(3, 7, "****");
        System.out.println(sb);
    }
    
}
