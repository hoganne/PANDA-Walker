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

        PaymentGateway paymentGateway = new PaymentGatewayImpl();
        AdvancedPayGateway advancedPayGateway = new AdvancedPaymentGatewayAdapter(paymentGateway);

        String mobile1 = null;

        String mobile2 = null;
        advancedPayGateway.makePayment(mobile1, mobile2);

    }
}
