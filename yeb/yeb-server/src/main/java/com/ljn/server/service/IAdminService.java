package com.ljn.server.service;

import com.ljn.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljn.server.pojo.Role;
import com.ljn.server.pojo.res.RespBean;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password, String code, HttpServletRequest request);

    Admin getAdminByUsername(String username);

    List<Role> getRolesById(Integer id);

    List<Admin> getAllAdmin(String keywords);

    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);

    RespBean updateAdminUserface(String url, Integer id, Authentication authentication);
}
