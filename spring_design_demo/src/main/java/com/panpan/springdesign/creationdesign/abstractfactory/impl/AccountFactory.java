package com.panpan.springdesign.creationdesign.abstractfactory.impl;

import com.panpan.springdesign.creationdesign.Factorypattern.Account;
import com.panpan.springdesign.creationdesign.Factorypattern.impl.CurrentAccount;
import com.panpan.springdesign.creationdesign.Factorypattern.impl.SavingAccount;
import com.panpan.springdesign.creationdesign.abstractfactory.AbstractFactory;
import com.panpan.springdesign.creationdesign.abstractfactory.Bank;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class AccountFactory extends AbstractFactory {
    final String CURRENT_ACCOUNT = "CURRENT";
    final String SAVING_ACCOUNT = "SAVING";
    @Override
    public Bank getBank(String bankName) {
        return null;
    }

    //use getAccount method to get object of type Account

    //It is factory method for object of type Account

    @Override
    public Account getAccount(String accountType){
        if(CURRENT_ACCOUNT.equals(accountType)) {
        return new CurrentAccount();
    } else if(SAVING_ACCOUNT.equals(accountType)){
            return new SavingAccount();
    }
        return null;
    }
}
