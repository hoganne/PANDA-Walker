package com.panpan.springdesign.creationdesign.Prototypedesign;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public abstract class Account implements Cloneable {
    abstract public void accountType();
    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
