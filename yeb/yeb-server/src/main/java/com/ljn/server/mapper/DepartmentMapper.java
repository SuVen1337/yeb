package com.ljn.server.mapper;

import com.ljn.server.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartments(Integer parentId);

    void addDep(Department department);

    void deleteDep(Department department);
}
