<template>
  <el-tabs v-model="activeName" type="card" >
    <el-tab-pane label="部门管理" name="DeptMana"><DeptMana/></el-tab-pane>
    <el-tab-pane label="职位管理" name="PosMana"><PosMana/></el-tab-pane>
    <el-tab-pane label="职称管理" name="JoblevelMana"><JoblevelMana/></el-tab-pane>
    <el-tab-pane label="奖惩规则" name="EcMana"><EcMana/></el-tab-pane>
    <el-tab-pane label="权限组" name="PermissMana"><PermissMana/></el-tab-pane>
  </el-tabs>
</template>

<script>
import DeptMana from "@/components/sys/basic/DeptMana";
import EcMana from "@/components/sys/basic/EcMana";
import JoblevelMana from "@/components/sys/basic/JoblevelMana";
import PermissMana from "@/components/sys/basic/PermissMana";
import PosMana from "@/components/sys/basic/PosMana";
export default {
  name: "SysBasic",
  components: {PermissMana, EcMana, PosMana, JoblevelMana, DeptMana},
  data(){
    return {
      activeName:'DeptMana'
    }
  }
}
</script>

<style scoped>

</style>