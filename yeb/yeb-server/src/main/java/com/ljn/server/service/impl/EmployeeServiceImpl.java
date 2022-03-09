package com.ljn.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljn.server.mapper.MailLogMapper;
import com.ljn.server.pojo.Employee;
import com.ljn.server.mapper.EmployeeMapper;
import com.ljn.server.pojo.MailConstants;
import com.ljn.server.pojo.MailLog;
import com.ljn.server.pojo.res.ResPageBean;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ljn
 * @since 2022-02-18
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    MailLogMapper mailLogMapper;

    @Override
    public ResPageBean getAllEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeeIPage = employeeMapper.getAllEmployeeByPage(page,employee,beginDateScope);
        ResPageBean resPageBean = new ResPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
        return resPageBean;
    }

    @Override
    public List<Employee> getAllEmployee(Integer id) {
        return employeeMapper.getAllEmployee(id);
    }

    @Override
    public ResPageBean getEmployeeSalary(Integer currentpage, Integer size) {
        Page<Employee> page = new Page<>(currentpage,size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeSalary(page);
        ResPageBean resPageBean = new ResPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
        return resPageBean;
    }


    @Override
    public RespBean getMaxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        return RespBean.success(null,String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString())+1));
    }

    @Override
    public RespBean addEmp(Employee employee) {
        //处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if(1==employeeMapper.insert(employee)){
            Employee emp = employeeMapper.getAllEmployee(employee.getId()).get(0);
            //数据库记录发送消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            //发送信息
            rabbitTemplate.convertAndSend( MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp
            ,new CorrelationData(msgId));
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


}
