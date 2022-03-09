package com.ljn.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: ljn
 * @Date: 2022/02/19/20:32
 * @Description:
 */
@MapperScan("com.ljn.server.mapper")
@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class YebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
