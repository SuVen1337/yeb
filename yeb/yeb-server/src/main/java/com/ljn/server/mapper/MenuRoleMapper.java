package com.ljn.server.mapper;

import com.ljn.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertMenuRole(Integer rid, Integer[] mids);
}
