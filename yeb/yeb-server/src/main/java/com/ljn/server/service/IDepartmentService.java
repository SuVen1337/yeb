package com.ljn.server.service;

import com.ljn.server.pojo.Department;
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
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartments();

    RespBean addDepartment(Department department);

    RespBean deleteDepartment(Integer id);
}
