package com.panpan.springdesign.creationdesign.abstractfactory.impl;

import com.panpan.springdesign.creationdesign.abstractfactory.Bank;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class YesBank implements Bank {

    public void bankName() {
        System.out.println("Yes Bank Pvt. Ltd.");
    }
}
