package com.panpan.springdesign.creationdesign.abstractfactory.impl;

import com.panpan.springdesign.creationdesign.abstractfactory.Bank;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class ICICIBank implements Bank {
    public void bankName() {
         System.out.println("ICICI Bank Ltd.");
    }
}
