package com.ljn.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ljn.server.pojo.Employee;
import com.ljn.server.pojo.Salary;
import com.ljn.server.pojo.res.ResPageBean;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IEmployeeService;
import com.ljn.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ljn
 * @Date: 2022/03/01/15:12
 * @Description:员工账套
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    ISalaryService salaryService;
    @Autowired
    IEmployeeService employeeService;

    @ApiOperation("查询所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation("获取所有员工账套")
    @GetMapping("/")
    public ResPageBean getEmployeeSalary(@RequestParam(defaultValue = "1") Integer currentpage,
                                         @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeSalary(currentpage,size);

    }
    @ApiOperation("更新员工账套")
    @PutMapping("/")
    public RespBean updateEmployeeSalary(Integer eid, Integer sid){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

}
