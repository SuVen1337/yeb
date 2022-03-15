<template>
  <div>
    <div>
      <el-input
          class="addInfo"
          size="small"
          placeholder="添加职位"
          suffix-icon="el-icon-plus"
          v-model="pos.name"
          @keydown.enter.native="addPosition"
          >
      </el-input>
      <el-button type="primary" size="small" icon="el-icon-plus" @click="addPosition">添加</el-button>
    </div>
    <div>
      <el-table
          class="posTable"
          border
          :data="positions"
          style="width: 80%"
          @selection-change="handleSelectionChange">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="id"
            label="编号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="name"
            label="职位"
            width="180">
        </el-table-column>
        <el-table-column
            prop="createDate"
            label="创建时间"
            width="180">
        </el-table-column>
        <el-table-column
            prop="enabled"
            label="是否启用"
            width="180">
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
                @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-button type="danger" size="small" style="margin-top: 10px" @click="deleteAll" :disabled="multipleSelection.length==0">批量删除</el-button>
    <el-dialog
        title="编辑职位"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <el-tag >职位名称</el-tag>
        <el-input v-model="updatePos.name" size="small" class="editInput"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="updatePosition">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PosMana",
  data(){
     return{
       pos:{
         name:''
       },
       positions:[],
       dialogVisible:false,
       updatePos: {
         name:''
       },
       multipleSelection: []
     }
  },
  mounted() {
    this.initPositions();
  },
  methods:{
    deleteAll(){
      this.$confirm('此操作将永久删除'+this.multipleSelection.length+'个职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let ids = '?';
        this.multipleSelection.forEach(item=>{
          ids+='ids='+item.id+'&';
        })
        this.deleteRequest('/system/basic/pos/'+ids).then(resp=>{
          if(resp){
            this.initPositions();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    addPosition(){
      if(this.pos.name){
        this.postRequest('/system/basic/pos/',this.pos).then(resp=>{
          if(resp){
            this.initPositions();
          }
        })
      }else {
        this.$message.error("职位名不能为空");
      }
    },
    initPositions(){
      this.getRequest("/system/basic/pos/").then(resp=>{
        if(resp){
          this.positions = resp;
        }
      })
    },
    handleEdit(row){
      Object.assign(this.updatePos,row);
      this.updatePos.createDate='';
      this.dialogVisible=true;
    },
    updatePosition(){
      this.putRequest("/system/basic/pos/",this.updatePos).then(resp=>{
        if(resp){
          this.initPositions();
        }
      })
      this.dialogVisible=false;
    },
    handleDelete(row){
      this.$confirm('此操作将永久删除['+row.name+']职位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest('/system/basic/pos/'+row.id).then(resp=>{
          if(resp){
            this.initPositions();
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
        this.multipleSelection = val
    }
  }
}
</script>

<style scoped>
.addInfo{
  width: 300px;
  margin-right: 8px;
}
.posTable{
  margin-top: 10px;
}
.editInput{
  width: 320px;
  margin-left: 10px;
}
</style>