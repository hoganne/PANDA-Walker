package com.example.consumer.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.example.payment.common.CommonResult;
import com.example.payment.entity.Payment;
import javax.annotation.Resource;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/26 13:31
 * @Version V1.0
 **/
@RestController
public class OrderController {
    public static final String PAYMENT_URL="http://localhost:8080";
    public static final String PAYMENT_URL_EUREKA="";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/comsumer/payment/create")
    public com.example.payment.common.CommonResult create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
}
