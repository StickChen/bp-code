package com.shallop.bpc.collection.basic.jdk8;

/**
 * @author StickChen
 * @date 2017/8/3
 */
public class Result {

//    public E get() {
//        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
//        Class clazz = (Class)type.getActualTypeArguments()[0];
//        E aClass = null;
//        try {
//            aClass = (E) clazz.getClass().newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return aClass;
//    }

    public <T extends Result> T get() {
        return (T) this;
    }

    public static void main(String[] args) {
        Result resultChild = new Result();
        ResultChild result = resultChild.get();
    }
}

class ResultChild extends Result{

}