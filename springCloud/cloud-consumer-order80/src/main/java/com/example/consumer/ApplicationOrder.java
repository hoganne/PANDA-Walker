package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/26 13:25
 * @Version V1.0
 **/
@SpringBootApplication
@EnableEurekaClient
public class ApplicationOrder {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationOrder.class,args);
    }
}
