package com.shallop.bpc.collection.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author StickChen
 * @date 2017/6/4
 */
public class Printer {

    public static void pt(Object obj) {
        System.out.println(obj);
    }

    public static void pts(Object obj) {
        System.out.println(JSON.toJSONString(obj));
    }


}
