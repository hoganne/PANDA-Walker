package com.panpan.springdesign.creationdesign.Prototypedesign;

import java.util.HashSet;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public abstract class Account implements Cloneable {
    private HashSet<Object> cloneHash=new HashSet<Object>();
    abstract public void accountType();
    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
            if(this.getClass().equals(SavingAccount.class)){
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setSavingAccount((SavingAccount) clone);
                ((SavingAccount)clone).setCurrentAccount(currentAccount);
            }else{
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setCurrentAccount((CurrentAccount) clone);
                ((CurrentAccount)clone).setSavingAccount(savingAccount);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
