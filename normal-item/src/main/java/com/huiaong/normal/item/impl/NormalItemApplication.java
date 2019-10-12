package com.huiaong.normal.item.impl;

import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NormalItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalItemApplication.class, args);
        Main.main(args);
    }

}
