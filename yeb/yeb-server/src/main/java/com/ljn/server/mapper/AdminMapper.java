package com.ljn.server.mapper;

import com.ljn.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljn.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface AdminMapper extends BaseMapper<Admin> {

     List<Role> getRolesById(Integer id);

    List<Admin> getAllAdmin(Integer id, String keywords);
}
