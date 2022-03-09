package com.ljn.server.controller;


import com.ljn.server.pojo.Admin;
import com.ljn.server.pojo.Menu;
import com.ljn.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuById(){
        return menuService.getMenuById();
    }
}
