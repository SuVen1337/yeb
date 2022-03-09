package com.ljn.server.mapper;

import com.ljn.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
    * @Description: 通过id查询菜单列表
    * @Param: [id]
    * @return: java.util.List<com.ljn.server.pojo.Menu>
    */
    List<Menu> getMenuById(Integer id);

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

}
