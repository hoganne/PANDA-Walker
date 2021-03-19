package com.panpan.springdesign.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Description
 * @Author xupan
 * @Date2021/3/19 17:30
 * @Version V1.0
 **/
@Configuration
public class RootConfig {
    @Bean
    public WebApplicationContext createRootContext(){
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        return (WebApplicationContext) annotationConfigApplicationContext;
    }
}
