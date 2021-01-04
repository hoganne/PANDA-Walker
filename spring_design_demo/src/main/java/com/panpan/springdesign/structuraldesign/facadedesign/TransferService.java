package com.panpan.springdesign.structuraldesign.facadedesign;

import com.panpan.springdesign.structuraldesign.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 17:00
 * @Version V1.0
 **/
public class TransferService {
    public static void transfer(int amount, Account fromAccount, Account toAccount) {
        System.out.println("=Transfering Money"+amount);
    }
}
