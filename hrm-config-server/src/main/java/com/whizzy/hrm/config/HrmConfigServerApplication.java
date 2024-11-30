package com.whizzy.hrm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HrmConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmConfigServerApplication.class, args);
    }

}
