package com.ljn.mail;

import com.ljn.server.pojo.Employee;
import com.ljn.server.pojo.MailConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: ljn
 * @Date: 2022/03/01/10:50
 * @Description:消息接收者
 */
@Component
public class MailRecevier {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailRecevier.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void  handler(Message message, Channel channel){
        Employee employee = (Employee) message.getPayload();
        MessageHeaders messageHeaders = message.getHeaders();
        //消息序号
        long tag = (long) messageHeaders.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String)messageHeaders.get("spring_returned_message_correlation");
        HashOperations hashOperations = redisTemplate.opsForHash();

        try{
            if(hashOperations.entries("mail_log").containsKey(msgId)){
                LOGGER.error("消息已被消费");
                //手动确认消息，tag消息序号，multiple是否确认多条
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
            helper.setSubject("入职欢迎邮件");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("joblevelName",employee.getJobLevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail",context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
            LOGGER.info("邮件发送成功");
            //将消息id存入redis
            hashOperations.put("mail_log",msgId,"OK");
            //手动确认消息
            channel.basicAck(tag,false);
        }catch (Exception e ){
            try {
                //手动确认消息，tag消息序号，multiple是否确认多条，requeue是否回到队列
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            LOGGER.error("邮件发送失败");
        }
    }

}
