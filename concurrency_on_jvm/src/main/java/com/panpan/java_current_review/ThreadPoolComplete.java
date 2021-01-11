package com.panpan.java_current_review;

import java.util.concurrent.*;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2021/1/7
 **/
public class ThreadPoolComplete {
    public static void main(String[] args) {
        ThreadPoolExecutor testPools = new ThreadPoolExecutor(100, 150,
                10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread("hahah");
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
    }
}
