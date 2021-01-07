package com.panpan.java_current_review;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author xupan
 * @Date2021/1/7 14:50
 * @Version V1.0
 **/
public class OrderExecuteThread {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition conditionA = lock.newCondition();
        final Condition conditionB = lock.newCondition();
        final Condition conditionC = lock.newCondition();

        for (int i = 1; i <=3; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();

                    try {
                        int count =0;
                        switch (finalI){
                            case 1:
                                while (count<5){
                                    conditionC.await();
                                    System.out.println("A");
                                    conditionA.signal();
                                    count++;
                                }
                                break;
                            case 2:
                                while (count<5){
                                    conditionA.await();
                                    System.out.println("B");
                                    conditionB.signal();
                                    count++;
                                }
                                break;
                            case 3:
                                while (count<5){
                                    conditionB.await();
                                    System.out.println("C");
                                    conditionC.signal();
                                    count++;
                                }
                                break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                     lock.unlock();
                    }

                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        try {
            conditionC.signal();
        }finally {
            lock.unlock();
        }

    }
}
