package com.moneyawapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = MoneyawApiApplication.class)
public class MoneyawApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyawApiApplication.class, args);
    }

}
