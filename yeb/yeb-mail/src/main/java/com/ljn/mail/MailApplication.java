package com.ljn.mail;

import com.ljn.server.pojo.MailConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: ljn
 * @Date: 2022/02/19/20:32
 * @Description:
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class,args);
    }
    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
