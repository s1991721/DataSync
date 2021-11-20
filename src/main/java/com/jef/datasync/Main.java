package com.jef.datasync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Jef
 * @Date: 2021/11/16 15:51
 * @Description
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jef.datasync.mapper")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
