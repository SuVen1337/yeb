package com.ljn.server.controller;

import com.ljn.server.pojo.Admin;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: ljn
 * @Date: 2022/03/01/17:09
 * @Description:个人中心
 */
@RestController
public class AdminInfoController {

    @Autowired
    IAdminService adminService;

    @ApiOperation("更新当前用户信息")
    @PutMapping("/admin/info")
    public RespBean updateAdminMsg(@RequestBody Admin admin , Authentication authentication){
        if(adminService.updateById(admin)){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,
                    authentication.getAuthorities()));
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
    @ApiOperation("修改密码")
    @PutMapping("/amdin/pass")
    public RespBean updateAdminPassword(@RequestBody Map<String,Object> info){
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass,pass,adminId);
    }
}
