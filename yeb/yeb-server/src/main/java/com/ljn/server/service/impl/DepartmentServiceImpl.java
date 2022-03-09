package com.ljn.server.service.impl;

import com.ljn.server.pojo.Department;
import com.ljn.server.mapper.DepartmentMapper;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(1==department.getResult()){
            return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败");

    }

    @Override
    public RespBean deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if(-2==department.getResult()){
            return RespBean.error("该部门下还有子部门，删除失败");
        }
        if(-1==department.getResult()){
            return RespBean.error("该部门下还有员工，删除失败");
        }
        if(1==department.getResult()){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
