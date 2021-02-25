package com.panpan.springdesign.Behavioraldesign.observer_design_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/2/25 11:31
 * @Version V1.0
 **/
public class ObserverPatternDemo {
    public static void main(String[] args) {
        Subject subject = new Subject();
        new HexaObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);
    }
}
