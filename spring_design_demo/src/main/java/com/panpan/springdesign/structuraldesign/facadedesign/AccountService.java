package com.panpan.springdesign.structuraldesign.facadedesign;

import com.panpan.springdesign.structuraldesign.Account;
import com.panpan.springdesign.structuraldesign.SavingAccount;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 17:00
 * @Version V1.0
 **/
public class AccountService {
    public static Account getAccount(String accountId) {
        return new SavingAccount();
    }
}
