package com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl;

import com.panpan.springdesign.Behavioraldesign.bridgedesign.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:49
 * @Version V1.0
 **/
public class SavingAccount implements Account {
    public Account openAccount() {
        System.out.println("OPENED: SAVING ACCOUNT ");
        return new SavingAccount();
    }

    public void accountType() {
        System.out.println("##It is a SAVING Account##");
    }
}
