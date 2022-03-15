<template>
  <div>
    <div>
      <el-input placeholder="请输入角色英文名" v-model="role.name" size="small" style="width: 290px;">
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input placeholder="请输入角色中文名称" v-model="role.nameZh" size="small" style="width: 290px;margin-left: 10px"/>
      <el-button type="primary" size="small" icon="el-icon-plus" @click="addRole" style="margin-left: 10px" >添加</el-button>
    </div>
    <div>
      <el-collapse v-model="activeName" accordion class="permissMain" @change="change">
        <el-collapse-item :title="item.nameZh" :name="item.id"  v-for="(item,index) in roles" :key="index">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>权限列表</span>
              <el-button style="float: right; padding: 3px 0;color: crimson"  type="text" icon="el-icon-delete" @click="deleteRole(item)">删除</el-button>
            </div>
            <div>
              <el-tree :data="allMenus"  show-checkbox
                       :props="defaultProps"
                        node-key="id"
                       :key="index"
                       ref="tree"
                       :default-checked-keys="selectMenus"></el-tree>
            </div>
            <div style="display: flex;justify-content: flex-end">
              <el-button size="mini" @click="activeName=-1">取消修改</el-button>
              <el-button size="mini" @click="updateTree(item.id,index)">确认修改</el-button>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "PermissMana",
  data(){
    return{
      role: {
        name:'',
        nameZh:'',
      },
      activeName: '2',
      roles:[],
      allMenus:[],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      selectMenus:[]
    }
  },
  mounted() {
    this.initRoles();
  },
  methods:{
    addRole(){
      if(this.role.nameZh&&this.role.name){
        this.postRequest('/system/basic/permiss/role',this.role).then(resp=>{
         if(resp){
            this.initRoles();
            this.role.name='';
            this.role.nameZh='';
          }
        })
      }else {
        this.$message("输入不能为空")
      }

    },
    deleteRole(item){
      this.$confirm('此操作将永久删除['+item.nameZh+']角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/permiss/role/'+item.id).then(resp=>{
          if(resp){
            this.initRoles();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    change(rid){
      if(rid){
        this.initAllMenus();
        this.initSelectMenus(rid);
      }
    },
    initSelectMenus(rid){
      this.getRequest('/system/basic/permiss/mid/'+rid).then(resp=>{
        if(resp){
          this.selectMenus = resp;
        }
      })
    },
    initAllMenus(){
      this.getRequest('/system/basic/permiss/menus').then(resp=>{
        if(resp){
          this.allMenus = resp;
        }
      })
    },
    initRoles(){
      this.getRequest('/system/basic/permiss/').then(resp=>{
          if(resp){
            this.roles = resp;
          }
      })
    },
    updateTree(id,index){
      let tree = this.$refs.tree[index]
      let selectedKeys = tree.getCheckedKeys(true);
      let url = '/system/basic/permiss/?rid=' + id;
      selectedKeys.forEach(key=>{
        url += '&mids='+ key;
      })
      this.putRequest(url).then(resp=>{
        if(resp){
          this.initRoles();
          this.activeName = '-1'
        }
      })
    }
  }
}
</script>

<style scoped>
.permissMain{
  margin-top: 10px;
  width: 700px;
}
</style>