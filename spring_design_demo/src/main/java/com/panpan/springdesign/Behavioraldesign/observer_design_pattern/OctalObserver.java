package com.panpan.springdesign.Behavioraldesign.observer_design_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/2/25 11:30
 * @Version V1.0
 **/
public class OctalObserver extends Observer{

    public OctalObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Octal String: "
                + Integer.toOctalString( subject.getState() ) );
    }
}
