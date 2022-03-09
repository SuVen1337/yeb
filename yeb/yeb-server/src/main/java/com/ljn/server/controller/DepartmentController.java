package com.ljn.server.controller;


import com.ljn.server.pojo.Department;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;

    @ApiOperation("获取所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @ApiOperation("添加部门")
    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department){
       return departmentService.addDepartment(department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/")
    public RespBean deleteDepartment(Integer id ){
        return departmentService.deleteDepartment(id);
    }
}
