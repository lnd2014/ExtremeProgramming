<template>
  <div id="app">
      <!--<router-link to="/">Home</router-link>-->
      <!--<router-link to="/about">About</router-link>-->
    <!--<router-view />-->

    <!--el布局容器-->
    <el-container>
      <!--顶部栏-->
      <el-header>通讯录管理系统</el-header>
      <el-container>
        <!--侧栏-->
        <el-aside width="200px">
          <!--导航菜单-->
          <el-menu default-active="1" router>
              <el-menu-item index="1" route="/">主页</el-menu-item>
              <el-menu-item index="2" route="/star">收藏</el-menu-item>
          </el-menu>
        </el-aside>
        <!--主要内容区-->
        <el-main>
          <!--显示路由内容-->
          <router-view />
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>



<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
    text-align: center;
  }
  
  .el-aside {
    color: #333;
    background-color: rgb(238, 241, 246);
  }
</style>
