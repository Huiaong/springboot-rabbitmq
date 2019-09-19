package com.huiaong.normal.trade.impl;

import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NormalTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NormalTradeApplication.class, args);
        Main.main(args);
    }

}
