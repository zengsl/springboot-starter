package com.ddp.res.web;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zzz
 */
@SpringBootApplication
@ComponentScan("com.ddp.res.service")
public class ResWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResWebApplication.class, args);
    }
}
