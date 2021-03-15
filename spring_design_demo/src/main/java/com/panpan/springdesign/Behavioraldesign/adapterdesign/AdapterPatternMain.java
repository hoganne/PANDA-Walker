package com.panpan.springdesign.Behavioraldesign.adapterdesign;

import com.panpan.springdesign.Behavioraldesign.adapterdesign.Impl.AdvancedPaymentGatewayAdapter;
import com.panpan.springdesign.Behavioraldesign.adapterdesign.Impl.PaymentGatewayImpl;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/2 16:14
 * @Version V1.0
 **/
public class AdapterPatternMain {
    public static void main(String[] args) {

//        PaymentGateway paymentGateway = new PaymentGatewayImpl();
//        AdvancedPayGateway advancedPayGateway = new AdvancedPaymentGatewayAdapter(paymentGateway);
//
//        String mobile1 = null;
//
//        String mobile2 = null;
//        advancedPayGateway.makePayment(mobile1, mobile2);



    }

    public int maxResult(char[][] source){
        int len=0;
        int tmp=0;
        int lenR=source.length;
        int lenC=source[0].length;
        if(lenC<=0||lenR<=0){
            return 0;
        }
        for (int i = 0; i <lenR ; i++) {
            for (int j = 0; j < lenC; j++) {
                if(source[i][j]=='1'){

                }
            }

        }
        return 1;
    }
}
