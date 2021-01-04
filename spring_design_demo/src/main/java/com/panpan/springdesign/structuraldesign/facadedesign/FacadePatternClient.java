package com.panpan.springdesign.structuraldesign.facadedesign;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/31 17:01
 * @Version V1.0
 **/
public class FacadePatternClient {
    public static void main(String[] args) {
        BankingServiceFacade serviceFacade = new BankingServiceFacadeImpl();
        serviceFacade.moneyTransfer();
    }
}
