<template>
  <el-container>
    <el-header>
      <div class="homeHeader">
        云E办
      </div>
      <el-dropdown class="userInfo"  @command="handleCommand">
          <span class="el-dropdown-link">
            {{user.name}}<i><img src="../static/qyc.jpg"></img></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
            <el-dropdown-item command="chat">聊天室</el-dropdown-item>
            <el-dropdown-item command="setting">设置</el-dropdown-item>
            <el-dropdown-item command="logout">注销登录</el-dropdown-item>
          </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu :router="true" unique-opened>
          <el-submenu :index="index+''" v-for="(item,index) in routes" :key="index" v-if="!item.hidden">
            <template slot="title">
              <i :class="item.iconCls" style="color:#1accff;margin-right: 5px"></i>
              <span>{{item.name}}</span>
            </template>
              <el-menu-item :index="children.path" v-for="(children,indexj) in item.children" :key="indexj">
                {{children.name}}
              </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <el-breadcrumb v-if="this.$router.currentRoute.path!='/home'" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
          欢迎来到云E办系统！
        </div>
        <router-view class="homeRoteView"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem('user'))
    }
  },
  methods: {
    handleCommand(command) {
      if(command=='logout'){
      this.$confirm('退出登录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

          this.postRequest('/logout');
          sessionStorage.removeItem('tokenStr');
          sessionStorage.removeItem('user');
          this.$store.commit('initRoutes',[])
          this.$router.replace("/")
        ;
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    };
      if(command=='chat'){
      this.$router.replace('/chat')
      }
    }
  },
  computed:{
      routes(){
        return this.$store.state.routes;
      }
  }
}
</script>

<style scoped>
.homeHeader{
  display: flex;
  /*align-items: center;*/
  /*justify-content: space-between;*/
  padding: 0 15px;
  /*box-sizing: border-box;*/
  font-size: 30px;
  font-family: 华文行楷;
  color: white;
}

.el-header{
  background-color: #409eff;
  color: #333;
  text-align: right ;
  line-height: 60px;
  display: flex;
}
.el-dropdown-link img{
  width: 48px;
  height: 48px;
  border-radius: 24px;
}
.userInfo{
  margin-left: 1269px;
}
.homeWelcome{
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409eff;
  padding-top: 50px;
}
.homeRoteView{
 margin-top: 10px;;
}

</style>