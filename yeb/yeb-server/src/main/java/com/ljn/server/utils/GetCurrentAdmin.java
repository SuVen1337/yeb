package com.ljn.server.utils;

import com.ljn.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: ljn
 * @Date: 2022/02/25/20:58
 * @Description:获取当前用户信息
 */
public class GetCurrentAdmin {

    public static Admin getPrinciple(){
        return ((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
