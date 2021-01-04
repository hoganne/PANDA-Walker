package com.panpan;

import scala.actors.threadpool.AtomicInteger;

import javax.xml.transform.Result;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 15:15
 * @Version V1.0
 **/
public class FastResult {
    public static CountDownLatch cd = new CountDownLatch(1);
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static  String result;
    public static synchronized void setResult(String message){
        FastResult.result =message;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            final int second = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(new Random(10).nextInt());
                        FastResult.result="123==="+second;
                        System.out.println(second);
                        cd.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            while (atomicInteger.get() == 1) {

            }
            cd.await();
            final String resultTmp = FastResult.result;
            System.out.println(resultTmp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
