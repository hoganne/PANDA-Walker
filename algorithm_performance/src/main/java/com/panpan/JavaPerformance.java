package com.panpan;

import org.apache.log4j.Logger;

/**
 * @Description
 * @Author xupan
 * @Date2020/11/30 10:36
 * @Version V1.0
 **/
public class JavaPerformance {
    public static Logger log = Logger.getLogger(JavaPerformance.class);
    public static void main(String[] args) {
//        log.debug("hahahha");
        new JavaPerformance().doTest();
    }

    public void doTest() {
        // Main Loop
        double l;
        long then = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            l = fibImpl1(50);
        }
        //...Elapsed time: 63736
        long now = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (now - then));
    }

    private double fibImpl1(int n) {
        if (n < 0) throw new IllegalArgumentException("Must be > 0");
        if (n == 0) return 0d;
        if (n == 1) return 1d;
        double d = fibImpl1(n - 2) + fibImpl1(n - 1);
        if (Double.isInfinite(d)) throw new ArithmeticException("Overflow");
        return d;
    }
}
