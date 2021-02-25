package com.panpan.springdesign.Behavioraldesign.observer_design_pattern;

/**
 * @Description
 * 监听器listener就是观察者
 * @Author xupan
 * @Date2021/2/25 11:30
 * @Version V1.0
 **/

public class BinaryObserver extends Observer{

    public BinaryObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Binary String: " + Integer.toBinaryString( subject.getState() ) );
    }
}
