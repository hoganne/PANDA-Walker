package com.panpan.java_current_review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
                try {
                    executor.getQueue().add(r);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ArrayList<Callable<String>> tasks = new ArrayList<Callable<String>>();
        tasks.add(new Callable() {
            @Override
            public String call() throws Exception {
                return "--haha-hha-";
            }
        });
//        try {
//            Object o = testPools.invokeAny((Collection<? extends Callable<Object>>) tasks);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}
