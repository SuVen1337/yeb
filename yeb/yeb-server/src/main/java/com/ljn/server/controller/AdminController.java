package com.ljn.server.controller;


import com.ljn.server.pojo.Admin;
import com.ljn.server.pojo.Role;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IAdminService;
import com.ljn.server.service.IRoleService;
import com.ljn.server.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    @ApiOperation("获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmin(String keywords){
        return adminService.getAllAdmin(keywords);
    }

    @ApiOperation("更新操作员")
    @PutMapping("/")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation("删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id){
        if(adminService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/roles")
    public List<Role> getAllroles(){
        return roleService.list();
    }

    @ApiOperation("更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId ,Integer[] rids){
        return adminService.updateAdminRole(adminId,rids);
    }
    @ApiOperation("更新用户头像")
    @PostMapping("/admin/userface")
    public RespBean updateAdminUserface(MultipartFile file, Integer id , Authentication authentication){
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl()+filePath[0]+"/"+filePath[1];
        return adminService.updateAdminUserface(url,id,authentication);
    }
}
