package com.shallop.bpc.collection.basic.concurrency.volatile_demo;

/**
 * @author StickChen
 * @date 2017/7/8
 */
public class VolatileOoO3 {
    private static int y = 0;
    private static int b = 0;
    private static int x = 0;
    private static int a = 0;
    private static int xx = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            int finalI = i;
            Thread one = new Thread(new Runnable() {
                public void run() {
                    // 由于线程one先启动，下面这句话让它等一等线程two. 读着可根据自己电脑的实际性能适当调整等待时间.
                    shortWait(60000 + finalI);
                    a = 1;
                    synchronized (VolatileOoO3.class){
                        x = b;  // 可重排序
                        xx = 2;
                    }
                }
            });

            Thread other = new Thread(new Runnable() {
                public void run() {
                    synchronized (VolatileOoO3.class){
                        xx = 2;
                        b = 1;  // 可重排序
                    }
                    y = a;
                }
            });
            one.start();other.start();
            one.join();other.join();
            if(x == 0 && y == 0) {
                System.err.println("第" + i + "次 (" + x + "," + y + "）");
                break;
            } else {
                System.out.println("第" + i + "次 (" + x + "," + y + "）");
                if (i % 10000 == 0) {
                }
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
