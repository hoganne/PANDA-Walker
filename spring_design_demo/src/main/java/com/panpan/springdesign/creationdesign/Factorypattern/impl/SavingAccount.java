package com.panpan.springdesign.creationdesign.Factorypattern.impl;

import com.panpan.springdesign.creationdesign.Factorypattern.Account;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class SavingAccount implements Account {
    public void accountType() {
        System.out.println("save account");
    }
}
