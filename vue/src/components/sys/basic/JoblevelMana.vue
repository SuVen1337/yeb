<template>
  <div>
    <div>
      <el-input
          style="width: 300px;margin-right: 8px;"
          size="small"
          placeholder="添加职称名称"
          suffix-icon="el-icon-plus"
          v-model="jl.name"
      ></el-input>
      <el-select v-model="jl.titleLevel" placeholder="请选择" size="small">
        <el-option
            v-for="item in levels"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button type="primary" size="small" icon="el-icon-plus" @click="addJobLevel" style="margin-left: 10px">添加</el-button>
    </div>
    <div>
        <el-table
            :data="jls"
            stripe
            style="width: 80%;margin-top: 10px"
            border
            @selection-change="handleSelectionChange">
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column
              prop="id"
              label="编号"
              width="150">
          </el-table-column>
          <el-table-column
              prop="name"
              label="职称名称"
              width="150">
          </el-table-column>
          <el-table-column
              prop="titleLevel"
              label="职称等级"
              width="150">
          </el-table-column>
          <el-table-column
              prop="createDate"
              label="创建日期"
              width="150">
          </el-table-column>
          <el-table-column
              prop="enabled"
              label="是否启用"
              width="150">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
              <el-tag type="danger" v-if="!scope.row.enabled">未启用</el-tag>
            </template>
          </el-table-column>
          <el-table-column
              label="操作"
              >
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  @click="editlj(scope.row)">编辑</el-button>
              <el-button
                  size="mini"
                  type="danger"
                  @click="deletejl(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      <el-button type="danger" size="small" style="margin-top: 10px" @click="deleteAll" :disabled="multipleSelection.length==0">批量删除</el-button>
      <el-dialog
          title="编辑职称"
          :visible.sync="dialogVisible"
          width="30%">
        <div>
          <el-tag >职称名称</el-tag>
          <el-input v-model="updateLevel.name" size="small" class="editInput" ></el-input>
          <el-tag >职称等级</el-tag>
          <el-input v-model="updateLevel.titleLevel" size="small" class="editInput" style="margin-top: 20px"></el-input>
        </div>
        <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="updatePosition">确 定</el-button>
  </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "JoblevelMana",
  data(){
    return{
      jl:{
        name:'',
        titleLevel:''
      },
      levels:['正高级','副高级','中级','初级','员级'],
      jls:[],
      multipleSelection:[],
      dialogVisible:false,
      updateLevel:{
        name:'',
        titleLevel:'',
      }
    }
  },
  mounted() {
    this.initJls()
  },
  methods:{
    editlj(row){
      this.dialogVisible= true
      Object.assign(this.updateLevel,row)
      this.updateLevel.createDate='';
    },
    initJls(){
      this.getRequest('/system/basic/joblevel/').then(resp=>{
        if(resp){
          this.jls=resp;
        }
      })
    },
    addJobLevel(){
      if(this.jl.name && this.jl.titleLevel){
        this.postRequest('/system/basic/joblevel/',this.jl).then(resp=>{
          if(resp){
            this.initJls();
          }
        })
      }else {
        this.$message({
          type:"warning",
          message:"输入不能为空"
        })
      }
    },
    deletejl(row){
      this.$confirm('此操作将永久删除'+row.name+'职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/system/basic/joblevel/"+row.id).then(resp=>{
          if(resp){
            this.initJls();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSelectionChange(val){
        this.multipleSelection = val;
    },
    deleteAll(){
      this.$confirm('此操作将永久删除'+this.multipleSelection.length+'个职称, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.multipleSelection.forEach(item=>{
          ids+='ids='+item.id+'&';
        })
        this.deleteRequest('/system/basic/joblevel/'+ids).then(resp=>{
          if(resp){
            this.initJls();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    updatePosition(){
      this.putRequest('/system/basic/joblevel/',this.updateLevel).then(resp=>{
        if(resp){
          this.initJls();
        }
      });
      this.dialogVisible= false
    }
  }
}
</script>

<style scoped>
.editInput{
  width: 320px;
  margin-left: 10px;
}
</style>