package com.panpan.springdesign.Behavioraldesign.adapterdesign.Impl;

import com.panpan.springdesign.Behavioraldesign.adapterdesign.PaymentGateway;
import com.panpan.springdesign.creationdesign.builder.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:10
 * @Version V1.0
 **/
public class PaymentGatewayImpl implements PaymentGateway {
    public void doPayment(Account account1, Account account2) {
        System.out.println("Do payment using Payment Gateway");
    }
}
