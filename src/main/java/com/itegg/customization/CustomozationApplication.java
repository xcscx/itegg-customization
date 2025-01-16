package com.itegg.customization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class CustomozationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomozationApplication.class, args);
    }

}
