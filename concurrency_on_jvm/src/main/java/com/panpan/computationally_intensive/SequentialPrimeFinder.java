package com.panpan.computationally_intensive;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/12/5
 **/
public class SequentialPrimeFinder extends AbstractPrimeFinder {
    @Override
    public int countPrimes(final int number) {
        return countPrimesInRange(1, number);
    }

    public static void main(final String[] args) {
        new SequentialPrimeFinder().timeAndCompute(Integer.parseInt(args[0]));
    }
}
