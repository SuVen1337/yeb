package com.ljn.server.service;

import com.ljn.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljn.server.pojo.res.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface IMenuService extends IService<Menu> {
    /**
    * @Description: 通过id查找菜单列表
    * @Param: []
    * @return: java.util.List<com.ljn.server.pojo.Menu>
    */
    List<Menu> getMenuById();
    
    /**
    * @Description: 根据角色获取菜单列表
    * @Param: []
    * @return: java.util.List<com.ljn.server.pojo.Menu>
    */
    List<Menu> getMenuWithRole();
    /**
     * @Description: 获取所有菜单及其子菜单
     * @Param: []
     * @return: java.util.List<com.ljn.server.pojo.Menu>
     */
    List<Menu> getAllMenus();

    /**
    * @Description: 更新角色菜单
    * @Param: [rid, mids]
    * @return: com.ljn.server.pojo.res.RespBean
    */
    RespBean updateMenuRole(Integer rid , Integer[] mids);
}
