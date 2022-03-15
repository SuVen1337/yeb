<template>
  <div>
    <div style="width: 500px">
      <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText">
      </el-input>

      <el-tree
          class="filter-tree"
          :data="deps"
          :props="defaultProps"
          :filter-node-method="filterNode"
          :expand-on-click-node="false"
          ref="tree">
         <span class="custom-tree-node" slot-scope="{ node, data }"style="display: flex;justify-content: space-between;width: 100%">
        <span>{{ node.label }}</span>
        <span>
          <el-button
              type="primary"
              size="mini"
              class="treeButton"
              @click="addDep(data)">
            增加部门
          </el-button>
          <el-button
              type="danger"
              size="mini"
              class="treeButton"
              @click="deleteDep(data)">
            删除部门
          </el-button>
        </span>
      </span>
      </el-tree>
    </div>
    <el-dialog
        title="编辑职称"
        :visible.sync="dialogVisible"
        width="30%">
      <div>
        <el-tag >上级部门</el-tag>
        <el-input v-model="pname" size="small" class="editInput" disabled></el-input>
        <el-tag >部门名称</el-tag>
        <el-input v-model="dep.name" size="small" class="editInput" ></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="doAdd">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "DeptMana",
  data(){
    return {
      filterText:'',
      deps:[],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      dialogVisible:false,
      dep:{
        name:'',
        parentId:-1,
      },
      pname:'',
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
    this.initDeps()
  },
  methods:{
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    initDeps(){
      this.getRequest('/system/basic/department/').then(resp=>{
        if(resp){
          this.deps = resp
        }
      })
    },
    deleteDep(data){
      this.$confirm('此操作将永久删除'+data.name+', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if(data.isParent){
          this.$message("父部门删除失败");
        }else {
        this.deleteRequest('/system/basic/department/?id='+data.id).then(resp=>{
          if(resp){
            this.deleteDepsByHand(null,this.deps,data.id);
          }
        })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    deleteDepsByHand(d,deps,id){
      for(let i=0;i<deps.length;i++){
        if(deps[i].id==id){
          deps.splice(i,1);
          if(deps.length==0){
            d.isParent=false;
          }
          return
        }else {
          this.deleteDepsByHand(deps[i],deps[i].children,id)
        }
      }

    },
    addDep(data){
      this.dep.parentId = data.id
      this.pname = data.name
      this.dialogVisible=true
    },
    doAdd(){
      if(this.dep.name&&this.dep.parentId){
        this.postRequest('/system/basic/department/',this.dep).then(resp=>{
          if(resp){
            this.initDepsByHand(this.deps,resp.obj);
          }
        })
      }else{
        this.$message("部门名称不能为空")
      }
      this.dialogVisible=false
      this.dep.name=''
    },
    initDepsByHand(deps,obj){
      for(let i=0;i<deps.length;i++){
        if(deps[i].id==obj.parentId){
          deps[i].children = deps[i].children.concat(obj);
          if(deps[i].children.length>0){
            deps[i].isParent = true;
          }
          return;
        }else {
          this.initDepsByHand(deps[i].children,obj)
        }
      }
    }
  }
}
</script>

<style scoped>
.treeButton{
  padding: 5px;
}
.editInput{
  width: 320px;
  margin-left: 10px;
}
</style>