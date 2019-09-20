package com.huiaong.normal.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NormalAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalAdminApplication.class, args);
    }

}
