package com.panpan.springdesign.creationdesign.abstractfactory.impl;

import com.panpan.springdesign.creationdesign.Factorypattern.Account;
import com.panpan.springdesign.creationdesign.abstractfactory.AbstractFactory;
import com.panpan.springdesign.creationdesign.abstractfactory.Bank;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class BankFactory extends AbstractFactory {
    final String ICICI_BANK = "ICICI";
    final String YES_BANK = "YES";

    //use getBank method to get object of name bank

    //It is factory method for object of name bank

    @Override
    public Bank getBank(String bankName) {
    if(ICICI_BANK.equalsIgnoreCase(bankName)){
        return new ICICIBank();
    } else if(YES_BANK.equalsIgnoreCase(bankName)){
        return new YesBank();
    }
        return null;
    }

    @Override
    public Account getAccount(String accountType) {
        return null;
    }
}
