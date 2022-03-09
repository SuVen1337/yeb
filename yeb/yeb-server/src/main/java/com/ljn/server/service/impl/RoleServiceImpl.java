package com.ljn.server.service.impl;

import com.ljn.server.pojo.Role;
import com.ljn.server.mapper.RoleMapper;
import com.ljn.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
