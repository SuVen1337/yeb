package com.ljn.server.service;

import com.ljn.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljn.server.pojo.res.ResPageBean;
import com.ljn.server.pojo.res.RespBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface IEmployeeService extends IService<Employee> {
    
    ResPageBean getAllEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean getMaxWorkId();

    RespBean addEmp(Employee employee);

    List<Employee> getAllEmployee(Integer id);

    ResPageBean getEmployeeSalary(Integer currentpage, Integer size);
}
