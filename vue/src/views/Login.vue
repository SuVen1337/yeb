<template>
  <div>
      <el-form  :rules="rules"
                ref="loginForm"
                :model="loginForm"
                class="loginContainer"
                v-loading="loading"
                element-loading-text="正在登录"
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">
          <h3 class="loginTitle">系统登录</h3>
          <el-form-item prop="username">
              <el-input type="text" auto-complete="false" v-model="loginForm.username"  placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item  prop="password">
              <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="code">
              <el-input type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击图片更换验证码" style="width:240px;margin-right: 5px"></el-input>
              <img :src="captchaUrl" @click="updateCaptcha" style="width:99px;vertical-align:middle;"/>
          </el-form-item>
          <el-checkbox v-model="checked" class="loginRemember">记住我</el-checkbox>
          <el-button type="primary" style="width:100%" @click="loginSubmmit">登录</el-button>
      </el-form>
  </div>
</template>

<script>

export default {
  name: "Login",
  data(){
    return{
      loginForm:{
        username:'',
        password:'',
        code:''
      },
      loading:false,
      captchaUrl:'/captcha?time='+new Date(),
      checked:true,
      rules:{
          username:[{required:true,message:'请输入用户名',trigger:'blur'}],
          password:[{required:true,message:'请输入密码',trigger:'blur'}],
          code:[{required:true,message:'请输入验证码',trigger:'blur'}],
      }
    }
  },
  methods:{
    updateCaptcha(){
      this.captchaUrl='/captcha?time='+new Date();
    },
    loginSubmmit(){
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true
          this.postRequest('/login',this.loginForm).then(resp=>{
            this.loading = false
            if(resp){
              const tokenStr = resp.obj.tokenHead + resp.obj.token;
              sessionStorage.setItem('tokenStr',tokenStr);
              this.$router.replace('/home')
            }
          })
        } else {
          this.$message.error('请输入所有字段');
          return false;
        }
      });
    }
  }
}

</script>

<style scoped>
  .loginContainer{
      border-radius: 15px;/*圆角边框*/
      background-clip: padding-box;
      margin: 180px auto;/*外边距*/
      width: 350px;/*设置元素的宽度*/
      padding: 15px 35px 15px;/*设置所有内边距属性*/
      background: #fff;
      border: 1px solid #eaeaea;
      box-shadow: 0 0 25px #cac6c6;/*添加阴影*/
  }
  .loginTitle{
    margin: 0px auto 40px auto;
    text-align: center;
  }
  .loginRemember{
    text-align: left;
    margin: 0px 0px 15px 0px;
  }
  .el-form-item__content{
    display: flex;
    align-items: center;
  }
</style>