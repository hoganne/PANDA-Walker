package com.panpan.java_current_review;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2021/1/3
 **/
public class BootStartExtend extends BootStart2 implements Cloneable{
    private static  Integer a=1;

    public static void main(String[] args) {
        reade();
    }
//    @Override
    public static void reade() {
//        Long
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                  a++;
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("heihei"+a);
    }
}
