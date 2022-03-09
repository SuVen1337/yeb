package com.ljn.server.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljn.server.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    IPage<Employee> getAllEmployeeByPage(Page<Employee> page, Employee employee, LocalDate[] beginDateScope);

    List<Employee> getAllEmployee(Integer id);

    IPage<Employee> getEmployeeSalary(Page<Employee> page);
}
