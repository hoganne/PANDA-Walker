package com.panpan.springdesign.creationdesign.abstractfactory;

import com.panpan.springdesign.creationdesign.Factorypattern.Account;
import com.panpan.springdesign.creationdesign.abstractfactory.impl.AccountFactory;
import com.panpan.springdesign.creationdesign.abstractfactory.impl.FactoryProducer;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class FactoryPatterMain {
    public static void main(String[] args) {

        AbstractFactory accountFactory = FactoryProducer.getFactory("ACCOUNT");

    //get an object of SavingAccount and call its accountType() method.

        Account savingAccount = accountFactory.getAccount("SAVING");

    //call accountType method of SavingAccount
        savingAccount.accountType();

    //get an object of CurrentAccount and call its accountType() method.

        Account currentAccount = accountFactory.getAccount("CURRENT");

    //call accountType method of CurrentAccount
        currentAccount.accountType();

    }
}
