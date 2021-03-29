package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/26 17:06
 * @Version V1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class ApplicationEureka {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEureka.class,args);
    }
}
