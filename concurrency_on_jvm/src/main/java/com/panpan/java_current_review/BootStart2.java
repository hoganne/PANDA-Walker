package com.panpan.java_current_review;

import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2021/1/2
 **/
public class BootStart2 {
    public static void main(String[] args) throws InterruptedException{
//      HashMap<Object, Object> map = new HashMap<>();
        ReentrantLock lock = new ReentrantLock();
        int i =0;
        Object o = new Object();
        Object o1 = new Object();
        if(o==o1&&o.equals(o1)){

        }
        System.out.println(o.hashCode());
//        synchronized (BootStart2.class){
//            BootStart2.class.wait(100);
//
//        }
//        final Thread flagTd = new Thread(new Runnable() {
//            @Override
//            public void run() {
//           try {
//                    int read = System.in.read();
//                    System.out.println(read);
////                    int count =100000000 ;
////                    int flag = 2;
////                    System.out.println("this is flag");
////                    while (count>3){
////                        System.out.println(count);
////                        count--;
////                        if(Thread.currentThread().isInterrupted()){
////                            break;
////                        }
////                    }
//                      notify();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                System.out.println("this is flag");
//            }
//        });
//        flagTd.start();
//        flagTd.interrupt();
//        Thread.sleep(100);
////      TimeUnit.SECONDS.sleep(1);
//        flagTd.wait(10);
//        flagTd.join();
////      flagTd.stop();
//
//        System.out.println("interrupt");
        }
    public final void read(){
        System.out.println("haha");
    }

}
