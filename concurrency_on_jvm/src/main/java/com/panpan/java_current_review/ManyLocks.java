package com.panpan.java_current_review;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author xupan
 * @Date2021/1/6 10:37
 * @Version V1.0
 **/
public class ManyLocks {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        Condition condition1 = lock.newCondition();
        final Thread main = Thread.currentThread();
        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"--准备获取lock");
//                    lock.lock();
                    try {
                        main.interrupt();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
//                        lock.unlock();
                        System.out.println("thread-0 unlock");
                    }
                }
            }).start();
            System.out.println(Thread.currentThread().getName()+"--准备获取lock");
            lock.lock();
            try {
                System.out.println("main--ready wait()");
                condition.await();
//              condition1.await();
                System.out.println("main--执行--"+System.currentTimeMillis());
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("interrupted--main--执行--"+System.currentTimeMillis());
            }finally {
                lock.unlock();
                System.out.println("unlock--"+System.currentTimeMillis());
            }
//            condition1.wait();
    }

}
