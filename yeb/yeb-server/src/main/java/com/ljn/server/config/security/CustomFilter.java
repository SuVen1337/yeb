package com.ljn.server.config.security;

import com.ljn.server.pojo.Menu;
import com.ljn.server.pojo.Role;
import com.ljn.server.service.IMenuService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @Author: ljn
 * @Date: 2022/02/24/17:11
 * @Description:权限控制，根据请求url分析请求所需角色
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation)object).getRequestUrl();
        //获取权限与url之间的关系数据
        List<Menu> menus = menuService.getMenuWithRole();
        for(Menu menu : menus){
            if(antPathMatcher.match(menu.getUrl(),requestUrl)) {
                //得到权限"ROLE_xx"
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
