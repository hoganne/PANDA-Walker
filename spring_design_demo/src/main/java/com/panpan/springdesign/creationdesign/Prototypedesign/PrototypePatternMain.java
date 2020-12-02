package com.panpan.springdesign.creationdesign.Prototypedesign;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class PrototypePatternMain {
    public static void main(String[] args) {
        Account currentAccount = (Account) AccountCache.accountCacheMap.get("CURRENT").clone();
        currentAccount.accountType();
        Account savingAccount = (Account) AccountCache.accountCacheMap.get("SAVING").clone();
        savingAccount.accountType();
    }
}
