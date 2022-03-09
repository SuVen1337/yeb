package com.ljn.server.mapper;

import com.ljn.server.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer insertAdminRole(Integer adminId, Integer[] rids);
}
