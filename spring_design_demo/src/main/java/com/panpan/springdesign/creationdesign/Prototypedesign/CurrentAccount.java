package com.panpan.springdesign.creationdesign.Prototypedesign;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class CurrentAccount extends Account {
    private SavingAccount savingAccount;

    public SavingAccount getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.savingAccount = savingAccount;
    }

    @Override
    public void accountType() {
        System.out.println("CURRENT ACCOUNT");
    }
}
