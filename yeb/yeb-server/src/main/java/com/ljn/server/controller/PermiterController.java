package com.ljn.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljn.server.pojo.Menu;
import com.ljn.server.pojo.MenuRole;
import com.ljn.server.pojo.Role;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IMenuRoleService;
import com.ljn.server.service.IMenuService;
import com.ljn.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: ljn
 * @Date: 2022/02/25/13:04
 * @Description:
 */
@RestController
@RequestMapping("/system/basic/permiss")
public class PermiterController {

    @Autowired
    IRoleService roleService;
    @Autowired
    IMenuService menuService;
    @Autowired
    IMenuRoleService menuRoleService;

    @ApiOperation("查询所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation("添加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }
    @ApiOperation("删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        if(roleService.removeById(rid)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }



    @ApiOperation("查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @ApiOperation("根据rid获取mid")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMenuId(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid))
                .stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation("更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid , Integer[] mids){

        return menuService.updateMenuRole(rid,mids);
    }
}
