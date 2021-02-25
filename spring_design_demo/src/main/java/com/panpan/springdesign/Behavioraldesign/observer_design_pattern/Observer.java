package com.panpan.springdesign.Behavioraldesign.observer_design_pattern;

/**
 * @Description
 * @Author xupan
 * @Date2021/2/25 11:29
 * @Version V1.0
 **/
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
