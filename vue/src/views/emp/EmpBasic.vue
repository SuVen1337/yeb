<template>
  <div>
    <div style="display: flex;">
      <div>
        <el-input v-model="empName" @keydown.enter.native="searchEpe" placeholder="通过用户名搜索用户"  prefix-icon="el-icon-search" size="small" style="width: 280px;margin-right: 10px"></el-input>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="searchEpe">搜索</el-button>
        <el-button type="primary" size="mini">
          <i class="fa fa-angle-double-down" arria-hideen="true"></i>高级搜索
        </el-button>
      </div>
      <div style="margin-left: 420px">
        <el-upload
            style="display: inline-flex"
            :show-file-list="false"
            :headers="headers"
            action="/employee/basic/import"
            >
          <el-button type="success" size="mini" style="margin-top: 2px;margin-left: 8px"><i class="fa fa-level-down" arria-hideen="true"></i>导入员工数据</el-button>
        </el-upload>
        <el-button type="success" @click="exportE" size="mini" style="margin-top: 2px;margin-left: 8px"><i class="fa fa-level-up" arria-hideen="true"></i>导出员工数据</el-button>
        <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-top: 2px;margin-left: 8px" @click="dialogVisible=true" >添加员工</el-button>
      </div>
    </div>
    <div>
      <el-table
          :data="emps"
          style="width: 100%;margin-top: 10px;"
          border
          :cell-style="{padding:'8px 0'}"
          class="table">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="workID"
            label="工号"
            width="100">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="70"
            fixed="left">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            width="70">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="出生日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="idCard"
            label="身份证号"
            width="170">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻状况"
            width="100">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族"
            width="70">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="常住地"
            width="100">
        </el-table-column>
        <el-table-column
            prop="politicsStatus.name"
            label="政治面貌"
            width="120">
        </el-table-column>
        <el-table-column
            prop="email"
            label="邮箱"
            width="200">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            width="120">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址"
            width="350">
        </el-table-column>
        <el-table-column
            prop="department.name"
            label="部门"
            width="150">
        </el-table-column>
        <el-table-column
            prop="jobLevel.name"
            label="职称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="position.name"
            label="职位"
            width="150">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式"
            width="100">
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            label="学历"
            width="70">
        </el-table-column>
        <el-table-column
            prop="specialty"
            label="专业"
            width="150">
        </el-table-column>
        <el-table-column
            prop="school"
            label="毕业学校"
            width="150">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            label="入职日期"
            width="100">
        </el-table-column>
        <el-table-column
            prop="workState"
            label="在职状态"
            width="100">
        </el-table-column>
        <el-table-column
            prop="contractTerm"
            label="合同年限"
            width="100">
          <template slot-scope="scope">
            <el-tag>{{scope.row.contractTerm}}</el-tag>年
          </template>
        </el-table-column>
        <el-table-column
          prop="conversionTime"
          label="转正日期"
          width="100">
      </el-table-column>
        <el-table-column
            prop="beginContract"
            label="合同起始日期"
            width="150">
        </el-table-column>
        <el-table-column
            prop="endContract"
            label="合同结束日期"
            width="150">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="200">
          <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.row)" style="padding: 3px">编辑</el-button>
          <el-button
              size="mini"
              type="primary"
              @click="handleDelete(scope.row)" style="padding: 3px">查看高级资料</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)" style="padding: 3px">删除</el-button>
        </template>
        </el-table-column>
      </el-table>
      <div style="display: flex;justify-content: flex-end">
        <el-pagination
            background
            layout=" prev, pager, next, jumper, ->, total"
            :total="total"
            :page-size="size"
            :current-page.sync="currentPage"
            @current-change="updateEpe">
        </el-pagination>
      </div>
      <div>
        <el-dialog
            title="添加员工"
            :visible.sync="dialogVisible"
            width="80%">
          <div>
            <el-form ref="empForm" :model="emp" :inline="true">
              <el-row>
                <el-col :span="6">
                  <el-form-item label="姓名:" prop="name">
                    <el-input v-model="emp.name" prefix-icon="el-icon-edit" placeholder="请输入员工姓名" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="性别:" prop="gender">
                      <el-radio-group v-model="emp.gender">
                        <el-radio  label="男">男</el-radio>
                        <el-radio  label="女">女</el-radio>
                      </el-radio-group>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="出生日期:" prop="birthday">
                      <el-date-picker
                          v-model="emp.birthday"
                          type="date"
                          size="mini"
                          style="width: 150px"
                          value-format="yyyy-MM-dd"
                          placeholder="出生日期">
                      </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="政治面貌:" prop="politicId">
                    <el-select v-model="emp.politicId" placeholder="请选择" size="mini" style="width: 150px">
                      <el-option
                          v-for="item in emps"
                          :key="item.index"
                          :label="item.label"
                          :value="item.politicsStatus.name"
                          >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="民族:" prop="nationId">
                    <el-select v-model="emp.nationId" placeholder="请选择" size="mini" style="width: 150px">
                      <el-option
                          v-for="item in emps"
                          :key="item.index"
                          :label="item.label"
                          :value="item.nation.name"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="籍贯:" prop="nativePlace">
                    <el-input v-model="emp.nativePlace" prefix-icon="el-icon-edit" placeholder="请输入籍贯" size="mini" style="width: 120px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="电子邮箱:" prop="email">
                    <el-input v-model="emp.email" prefix-icon="el-icon-message" placeholder="请输入电子邮箱" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="联系地址:" prop="adress">
                    <el-input v-model="emp.address" prefix-icon="el-icon-edit" placeholder="请输入联系地址" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="职位:" prop="posId">
                    <el-select v-model="emp.posId" placeholder="请选择职位" size="mini" style="width: 150px">
                      <el-option
                          v-for="item in emps"
                          :key="item.index"
                          :label="item.label"
                          :value="item.nation.name"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="职称:" prop="posId">
                    <el-select v-model="emp.jobLevelId" placeholder="请选择职称" size="mini" style="width: 120px">
                      <el-option
                          v-for="item in emps"
                          :key="item.index"
                          :label="item.label"
                          :value="item.nation.name"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="所属部门:" prop="departmentId">
                    <el-input v-model="emp.departmentId" prefix-icon="el-icon-edit" placeholder="请输入所属部门" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="电话号码:" prop="phone">
                    <el-input v-model="emp.phone" prefix-icon="el-icon-phone" placeholder="请输入电话号码" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="工号:" prop="workID">
                    <el-input v-model="emp.workID" prefix-icon="el-icon-edit" placeholder="请输入工号" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="学历:" prop="tiptopDegree">
                    <el-select v-model="emp.tiptopDegree" placeholder="请选择学历" size="mini" style="width: 120px">
                      <el-option
                          v-for="item in emps"
                          :key="item.index"
                          :label="item.label"
                          :value="item.nation.name"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="所学专业:" prop="specialty">
                    <el-input v-model="emp.specialty" prefix-icon="el-icon-edit" placeholder="请输入专业" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="毕业院校:" prop="school">
                    <el-input v-model="emp.school" prefix-icon="el-icon-edit" placeholder="毕业院校" size="mini" style="width: 150px"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">

                </el-col>
                <el-col :span="6">

                </el-col>
                <el-col :span="6">

                </el-col>
                <el-col :span="6">

                </el-col>
              </el-row>
            </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import {downloadRequest} from "@/utils/download";

export default {
  name: "EmpBasic",
  data(){
    return{
      headers:{
        Authorization: sessionStorage.getItem('tokenStr')
      },
      emps:[],
      total:0,
      currentPage:1,
      size:10,
      empName:'',
      dialogVisible:false,
      emp:{
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: null,
          nativePlace: '',
          politicId: null,
          email:'',
          phone: '',
          address: '',
          departmentId: null,
          jobLevelId: null,
          posId:null,
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school:'' ,
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: null,
          conversionTime: '',
          notWorkDate: null,
          beginContract: '',
          endContract: '',
          workAge: null,
          salaryId: null,
      }
    }
  },
  mounted() {
    this.initEmps()
  },
  methods:{
    exportE(){
      this.downloadRequest('/employee/basic/export')
    },
    searchEpe(){
      this.currentPage=1;
      this.initEmps()
    },
    updateEpe(){
      this.initEmps()
    },
    initEmps(){
      this.getRequest('/employee/basic/?currentPage='+this.currentPage+'&size='+this.size+'&name='+this.empName).then(resp=>{
        if(resp){
          this.emps=resp.data
          this.total=resp.total
        }
      })
    }
  }
}

</script>

<style scoped>


</style>