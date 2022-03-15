<template>
  <div>
    <div style="display: flex;justify-content: center">
      <el-input placeholder="通过用户名搜索用户" v-model="keywords" prefix-icon="el-icon-search" size="small" style="width: 280px;margin-right: 10px"></el-input>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="searchAdmin">搜索</el-button>
    </div>
    <div class="adminContainer">
      <el-card  v-for="(item,index) in admins" :key="index" style="width: 300px;">
        <div slot="header" class="clearfix">
          <span>{{ item.name }}</span>
          <el-button style="float: right; padding: 3px 0;color: crimson" type="text" icon="el-icon-delete" @click="deleteAdmin(item)"></el-button>
          <el-button style="float: right; padding: 3px 0;color:#409eff;margin-right: 20px" type="text" icon="el-icon-edit" @click="editAdmin(item)"></el-button>
        </div>
        <div class="box-card">
          <div>编号：{{item.id}}</div>
          <div>姓名：{{item.name}}</div>
          <div>手机号：{{item.telephone}}</div>
          <div>座机：{{item.phone}}</div>
          <div>地址：{{item.address}}</div>
          <div>用户状态：<el-switch
              v-model="item.enabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用"
              disabled>
          </el-switch></div>
          <div>用户名：{{item.username}}</div>
          <div>
            用户角色
            <el-tag type="success" v-for="(role,index) in item.roles" :key="index" style="margin-left: 10px">{{role.nameZh}}</el-tag>
            <el-popover
                placement="bottom"
                title="角色列表"
                width="250px"
                trigger="click"
                @show="showPop(item)"
                @hide="updateRoles(item)"
            >
              <el-select v-model="selectRoles" multiple placeholder="请选择">
                <el-option
                    v-for="(role,index) in allRoles"
                    :key="index"
                    :label="role.nameZh"
                    :value="role.id">
                </el-option>
              </el-select>
              <el-button slot="reference" type="text" icon="el-icon-more" ></el-button>
            </el-popover>
          </div>
        </div>
      </el-card>
      <el-dialog
          title="编辑资料"
          :visible.sync="dialogVisible"
          width="30%">
        <div>
          <el-tag >编号</el-tag>
          <el-input v-model="updateAdmin.id" size="small" class="editInput" ></el-input>
          <el-tag >姓名</el-tag>
          <el-input v-model="updateAdmin.name" size="small" class="editInput" style="margin-top: 20px"></el-input>
          <el-tag >手机号</el-tag>
          <el-input v-model="updateAdmin.telephone" size="small" class="editInput" style="margin-top: 20px"></el-input>
          <el-tag >座机</el-tag>
          <el-input v-model="updateAdmin.phone" size="small" class="editInput" style="margin-top: 20px"></el-input>
          <el-tag >地址</el-tag>
          <el-input v-model="updateAdmin.address" size="small" class="editInput" style="margin-top: 20px"></el-input>
          <el-tag >是否启用</el-tag>
          <el-input v-model="updateAdmin.enabled" size="small" class="editInput" style="margin-top: 20px"></el-input>
          <el-tag >用户名</el-tag>
          <el-input v-model="updateAdmin.username" size="small" class="editInput" style="margin-top: 20px"></el-input>
        </div>
        <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysAdmin",
  data(){
    return{
      admins:[],
      keywords:'',
      dialogVisible:false,
      updateAdmin: {
        name:''
      },
      allRoles:[],
      selectRoles:[],
    }
  },
  methods:{
    showPop(item){
      this.initAllRoles();
      let roles =  item.roles;
      this.selectRoles = [];
      roles.forEach(role=>{
        this.selectRoles.push(role.id)
      })
    },
    updateRoles(item){
      let flag = false;
      let roles=[]
      Object.assign(roles,item.roles)
      if(roles.length!=this.selectRoles.length){
        flag = true
      }else {
        for (let i = 0; i < roles.length; i++){
          for (let j = 0; j < this.selectRoles.length; j++) {
            if (roles[i].id == this.selectRoles[j]) {
              roles.splice(i, 1);
              i--;
              break;
            }
          }
        }
        flag= !roles.length==0
      }
      if(flag){
        let url = '/system/admin/role?adminId='+item.id
        for(let i=0;i<this.selectRoles.length;i++){
          url += '&rids='+this.selectRoles[i]
        }
        this.putRequest(url).then(resp=>{
          if(resp){
            this.initAdmins(this.keywords);
          }
        })
      }
    },
    initAllRoles(){
      this.getRequest('/system/admin/roles').then(resp=>{
        if(resp){
          this.allRoles = resp;
        }
      })
    },
    editAdmin(item){
      Object.assign(this.updateAdmin,item)
      this.dialogVisible = true;
    },
    doUpdate(){
      this.putRequest("/system/admin/",this.updateAdmin).then(resp=>{
        if(resp){
          this.initAdmins(this.keywords);
        }
      })
      this.updateAdmin='';
      this.dialogVisible = false;
    },
    searchAdmin(){
      this.initAdmins(this.keywords);
    },
    initAdmins(keywords){
      this.getRequest('/system/admin/?keywords='+keywords).then(resp=>{
        if(resp){
          this.admins = resp
        }
      })
    },
    deleteAdmin(item){
      this.$confirm('此操作将永久删除['+item.name+'], 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/admin/'+item.id).then(resp=>{
          if(resp){
            this.initAdmins(this.keywords);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
}
</script>

<style scoped>
.adminContainer{
  display: flex;
  justify-content: space-around;
}
.box-card{
  font-size: 20px;
  font-family: 宋体;
  color: #409eff;
}
</style>