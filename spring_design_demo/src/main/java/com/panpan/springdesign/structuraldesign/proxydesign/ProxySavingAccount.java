package com.panpan.springdesign.structuraldesign.proxydesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 17:19
 * @Version V1.0
 **/
public class ProxySavingAccount implements Account{
    private Account savingAccount;
    public void accountType() {
        if(savingAccount == null){
            savingAccount = new SavingAccount();
        }
        savingAccount.accountType();
    }
}