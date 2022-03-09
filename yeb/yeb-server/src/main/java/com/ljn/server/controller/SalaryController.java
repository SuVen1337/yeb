package com.ljn.server.controller;


import com.ljn.server.pojo.Salary;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    ISalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation("删除工资账套")
    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id){
        if(salaryService.removeById(id)){
            return  RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("修改工资账套")
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if(salaryService.updateById(salary)){
            return  RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }
    @ApiOperation("添加工资账套")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if(salaryService.save(salary)){
            return  RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

}
