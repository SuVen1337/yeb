package com.ljn.server.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ljn.server.pojo.MailConstants;
import com.ljn.server.pojo.MailLog;
import com.ljn.server.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @Author: ljn
 * @Date: 2022/03/01/12:13
 * @Description:rabbitmq配置类
 *
 */
@Configuration
public class RabbitMqConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConfig.class);
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMailLogService mailLogService;


    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate= new RabbitTemplate(cachingConnectionFactory);
        //data:消息唯一标识，ack:确认结果，Cause:失败原因
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            String msgId = data.getId();
            if(ack){
                LOGGER.info("{}======>消息发送成功",msgId);
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("msgId",msgId));
            }else{
                LOGGER.error("{}======>消息发送失败",msgId);
            }
        });
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routingkey)->{
            LOGGER.error("{}======>消息发送queue时失败",msg.getBody());
        });

        return rabbitTemplate;
    }


    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}
