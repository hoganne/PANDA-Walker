package com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl;

import com.panpan.springdesign.Behavioraldesign.bridgedesign.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:50
 * @Version V1.0
 **/
public class CurrentAccount implements Account {
    
    public Account openAccount() {
        System.out.println("OPENED: CURRENT ACCOUNT ");
        return new CurrentAccount();

    }

    public void accountType() {
        System.out.println("##It is a CURRENT Account##");

    }
}
