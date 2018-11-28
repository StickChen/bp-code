package com.shallop.bpc.collection.basic.concurrency.volatile_demo;

/**
 * @author StickChen
 * @date 2017/7/8
 */
public class VolatileOoO2 {
    public static int y = 0;
    public volatile static int b = 0;
    public static int x = 0;
    public volatile static int a = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread one = new Thread(new Runnable() {
                public void run() {
                    // 由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
                    shortWait(60000);
                    a = 1;
                    x = b;  // 可重排序
                }
            });

            Thread other = new Thread(new Runnable() {
                public void run() {
                    b = 1;  // 可重排序
                    y = a;
                }
            });
            one.start();other.start();
            one.join();other.join();
            String result = "第" + i + "次 (" + x + "," + y + "）";
            if(x == 0 && y == 0) {
                System.err.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }


    public static void shortWait(long interval){
        long start = System.nanoTime();
        long end;
        do{
            end = System.nanoTime();
        }while(start + interval >= end);
    }
}
