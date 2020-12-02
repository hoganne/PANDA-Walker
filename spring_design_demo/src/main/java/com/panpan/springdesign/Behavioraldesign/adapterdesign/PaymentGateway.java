package com.panpan.springdesign.Behavioraldesign.adapterdesign;

import com.panpan.springdesign.creationdesign.builder.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:08
 * @Version V1.0
 **/
public interface PaymentGateway {
    void doPayment(Account account1, Account account2);
}
