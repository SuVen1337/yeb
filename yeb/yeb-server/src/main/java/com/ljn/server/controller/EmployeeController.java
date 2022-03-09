package com.ljn.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.ljn.server.pojo.*;
import com.ljn.server.pojo.res.ResPageBean;
import com.ljn.server.pojo.res.RespBean;
import com.ljn.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    IPoliticsStatusService politicsStatusService;
    @Autowired
    IJoblevelService joblevelService;
    @Autowired
    INationService nationService;
    @Autowired
    IPositionService positionService;
    @Autowired
    IDepartmentService departmentService;

    @ApiOperation("查询员工")
    @GetMapping("/")
    public ResPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   Employee employee, LocalDate[] beginDateScope ){
        return employeeService.getAllEmployeeByPage(currentPage,size,employee,beginDateScope);
    }

    @ApiOperation("获取所有政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @ApiOperation("获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.list();
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation("获取工号")
    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }

    @ApiOperation("添加员工")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation("更新员工")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateById(employee)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id){
        if(employeeService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("导出员工数据")
    @GetMapping(value = "/export",produces ="application/octet-stream" )
    public void exportEmployee(HttpServletResponse response){
        List<Employee> list = employeeService.getAllEmployee(null);
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params,Employee.class,list);
        ServletOutputStream out = null;
        try{
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder
                    .encode("员工表.xls","UTF-8"));
            out= response.getOutputStream();
            workbook.write(out);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation("导入员工数据")
    @PostMapping("/import")
    public RespBean importEmployee(MultipartFile file){
        ImportParams params =  new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        List<Nation> nations = nationService.list();
        List<Department> departments = departmentService.list();
        List<Joblevel> joblevels = joblevelService.list();
        List<PoliticsStatus> politicsStatuses = politicsStatusService.list();
        List<Position> positions = positionService.list();
        try{
            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(),Employee.class,params);
            list.forEach(employee -> {
                employee.setNationId(nations.get(nations.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setDepartmentId(departments.get(departments.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(joblevels.get(joblevels.indexOf(new Joblevel(employee.getJobLevel().getName()))).getId());
                employee.setPoliticId(politicsStatuses.get(politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setPosId(positions.get(positions.indexOf(new Position(employee.getPosition().getName()))).getId());
            });
            if(employeeService.saveBatch(list)){
                return RespBean.success("导入成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }



}
