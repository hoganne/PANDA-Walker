package com.panpan.springdesign.Behavioraldesign.adapterdesign.Impl;

import com.panpan.springdesign.Behavioraldesign.adapterdesign.AdvancedPayGateway;
import com.panpan.springdesign.Behavioraldesign.adapterdesign.PaymentGateway;
import com.panpan.springdesign.creationdesign.builder.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:13
 * @Version V1.0
 **/
public class AdvancedPaymentGatewayAdapter implements AdvancedPayGateway {

    private PaymentGateway paymentGateway;

    public AdvancedPaymentGatewayAdapter(PaymentGateway paymentGateway) {

        this.paymentGateway = paymentGateway;

    }

    public void makePayment(String mobile1, String mobile2) {

        Account account1 = null;
        //get account number by mobile number mobile

        Account account2 = null;
        //get account number by mobile number mobile

        paymentGateway.doPayment(account1, account2);

    }
}
