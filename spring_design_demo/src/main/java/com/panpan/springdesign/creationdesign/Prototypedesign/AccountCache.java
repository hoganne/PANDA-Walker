package com.panpan.springdesign.creationdesign.Prototypedesign;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/11/29
 **/
public class AccountCache {
    public static Map<String, Account> accountCacheMap = new HashMap<String, Account>();

    static{
        Account currentAccount = new CurrentAccount();
        Account savingAccount = new SavingAccount();
        ((CurrentAccount) currentAccount).setSavingAccount((SavingAccount) savingAccount);
        ((SavingAccount) savingAccount).setCurrentAccount((CurrentAccount) currentAccount);
        accountCacheMap.put("SAVING", savingAccount);
        accountCacheMap.put("CURRENT", currentAccount);

    }
}
