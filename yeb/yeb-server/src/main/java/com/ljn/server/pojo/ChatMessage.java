package com.ljn.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: ljn
 * @Date: 2022/03/01/16:31
 * @Description:消息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMessage {

    private String from;
    private String to;
    private String content;
    private LocalDateTime date;
    private String formNickName;
}
