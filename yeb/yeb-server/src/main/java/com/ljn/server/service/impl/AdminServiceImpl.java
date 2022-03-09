package com.ljn.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljn.server.config.security.JwtTokenUtil;
import com.ljn.server.mapper.AdminRoleMapper;
import com.ljn.server.pojo.Admin;
import com.ljn.server.mapper.AdminMapper;
import com.ljn.server.pojo.AdminRole;
import com.ljn.server.pojo.Role;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IAdminRoleService;
import com.ljn.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljn.server.utils.GetCurrentAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /**
    * @Description: 登陆之后返回token
    * @Param: [username, password, request]
    * @return: com.ljn.server.pojo.res.RespBean
    */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
            String captcha = (String)request.getSession().getAttribute("captcha");
            if(StringUtils.hasLength(code) && !captcha.equalsIgnoreCase(code)){
                return RespBean.error("验证码输入错误，请重新输入！");
            }
            //登录
            UserDetails userDetails =userDetailsService.loadUserByUsername(username);
            if(null==userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
                return RespBean.error("用户或密码不正确");
            }
            if(!userDetails.isEnabled()){
                return RespBean.error("账号被禁用，请联系管理员");
            }
            //更新Security登录用户对象
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            
            //生成token
            String token = jwtTokenUtil.generateToken(userDetails);
            Map<String,String> tokenMap=new HashMap<>();
            tokenMap.put("token",token);
            tokenMap.put("tokenHead",tokenHead);
            return RespBean.success("登录成功",tokenMap);
    }
    /**
    * @Description: 根据用户名查询用户信息
    * @Param: [username]
    * @return: com.ljn.server.pojo.Admin
    */
    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username",username)
                .eq("enabled",true));
    }
    /**
    * @Description: 根据id查询角色
    * @Param: [id]
    * @return: java.util.List<com.ljn.server.pojo.Role>
    */
    @Override
    public List<Role> getRolesById(Integer id) {

        return adminMapper.getRolesById(id);
    }

    @Override
    public List<Admin> getAllAdmin(String keywords) {
        Integer id = GetCurrentAdmin.getPrinciple().getId();
        return adminMapper.getAllAdmin(id,keywords);
    }

    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result = adminRoleMapper.insertAdminRole(adminId,rids);
        if(rids.length==result){
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败");
    }

    @Override
    public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPass,admin.getPassword())){
            admin.setPassword(encoder.encode(pass));
            if(adminMapper.updateById(admin)==1){
                return RespBean.success("修改成功");
            }
        }
        return RespBean.error("修改失败");

    }

    @Override
    public RespBean updateAdminUserface(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        if(adminMapper.updateById(admin)==1){
            Admin principal = (Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication
                    .getAuthorities()));
            return RespBean.success("更新成功",url);
        }
        return RespBean.error("更新失败");
    }
}
