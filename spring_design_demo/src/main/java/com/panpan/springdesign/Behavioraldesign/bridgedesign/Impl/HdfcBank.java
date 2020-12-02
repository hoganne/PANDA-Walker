package com.panpan.springdesign.Behavioraldesign.bridgedesign.Impl;

import com.panpan.springdesign.Behavioraldesign.bridgedesign.Account;
import com.panpan.springdesign.Behavioraldesign.bridgedesign.Bank;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:56
 * @Version V1.0
 **/
public class HdfcBank extends Bank {
    public HdfcBank(Account account) {
        super(account);

    }

    @Override
    public Account openAccount() {

        System.out.print("Open your account with HDFC Bank");
        return account;

    }
}
