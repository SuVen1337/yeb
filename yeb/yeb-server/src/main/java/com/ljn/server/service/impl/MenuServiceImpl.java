package com.ljn.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljn.server.mapper.MenuRoleMapper;
import com.ljn.server.pojo.Admin;
import com.ljn.server.pojo.Menu;
import com.ljn.server.mapper.MenuMapper;
import com.ljn.server.pojo.MenuRole;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    MenuRoleMapper menuRoleMapper;
    /**
    * @Description: 根据id获取菜单列表
    * @Param: []
    * @return: java.util.List<com.ljn.server.pojo.Menu>
    */
    @Override
    public List<Menu> getMenuById(){
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //从redis查询数据
        List<Menu> menus=(List<Menu>)valueOperations.get("menu_"+adminId);
        //为空则查询数据库
        if(CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenuById(adminId);
            //将数据存入redis
            valueOperations.set("menu_"+adminId,menus);
        }
        return menus;
    }
    /**
     * @Description: 根据角色获取菜单列表
     * @Param: []
     * @return: java.util.List<com.ljn.server.pojo.Menu>
     */
    @Override
    public List<Menu> getMenuWithRole() {
        return menuMapper.getMenuWithRole();
    }

    /**
     * @Description: 获取所有菜单及其子菜单
     * @Param: []
     * @return: java.util.List<com.ljn.server.pojo.Menu>
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if(null==mids || mids.length==0){
            return RespBean.success("删除成功");
        }
        if(menuRoleMapper.insertMenuRole(rid,mids)==mids.length){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
