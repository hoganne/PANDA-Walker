package com.panpan.springdesign.creationdesign.Prototypedesign;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class SavingAccount extends Account {
    private CurrentAccount currentAccount;

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(CurrentAccount currentAccount) {
        this.currentAccount = currentAccount;
    }

    @Override
    public void accountType() {
        System.out.println("SAVING ACCOUNT");
    }
}
