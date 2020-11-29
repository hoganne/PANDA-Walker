package com.panpan.springdesign.creationdesign.abstractfactory.impl;
import com.panpan.springdesign.creationdesign.abstractfactory.AbstractFactory;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class FactoryProducer {
    final static String BANK = "BANK";
    final static String ACCOUNT = "ACCOUNT";
    public static AbstractFactory getFactory(String factory){
    if(BANK.equalsIgnoreCase(factory)){
            return new BankFactory();
    } else if(ACCOUNT.equalsIgnoreCase(factory)){
            return new AccountFactory();
    }
        return null;
    }
}
