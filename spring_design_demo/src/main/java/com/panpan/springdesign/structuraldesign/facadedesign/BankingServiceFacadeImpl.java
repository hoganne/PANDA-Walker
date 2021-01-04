package com.panpan.springdesign.structuraldesign.facadedesign;

import com.panpan.springdesign.structuraldesign.Account;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 17:01
 * @Version V1.0
 **/
public class BankingServiceFacadeImpl implements BankingServiceFacade{
    public void moneyTransfer() {
        if(PaymentService.doPayment()){
            Account fromAccount = AccountService.getAccount("1");
            Account toAccount = AccountService.getAccount("2");
            TransferService.transfer(1000, fromAccount, toAccount);
        }
    }
}
