package com.panpan.java_current_review;

import clojure.lang.Var;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2021/1/5
 **/
public class FuturesUse implements Callable<String> {
    public static void main(String[] args) {
        FutureTask<String> task = new FutureTask<String>(new FuturesUse());
        new Thread(task).start();
        try {
            String s = task.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String call() throws Exception {
        LockSupport.park();
        LockSupport.unpark(new Thread());
        return "null";
    }
}
