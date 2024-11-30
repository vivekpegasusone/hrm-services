package com.whizzy.hrm.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.whizzy.hrm.user", "com.whizzy.hrm.multitenant"})
public class HrmUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmUserApplication.class, args);
    }

}
