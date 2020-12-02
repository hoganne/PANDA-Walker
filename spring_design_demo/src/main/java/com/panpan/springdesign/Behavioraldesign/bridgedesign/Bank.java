package com.panpan.springdesign.Behavioraldesign.bridgedesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:51
 * @Version V1.0
 **/
public abstract class Bank {
    //Composition with implementor
    protected Account account;

    public Bank(Account account) {

        this.account = account;

    }

    protected abstract Account openAccount();
}
