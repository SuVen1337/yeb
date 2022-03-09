package com.ljn.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljn.server.mapper.EmployeeMapper;
import com.ljn.server.pojo.Employee;
import com.ljn.server.pojo.EmployeeEc;
import com.ljn.server.mapper.EmployeeEcMapper;
import com.ljn.server.pojo.res.ResPageBean;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IEmployeeEcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

}
