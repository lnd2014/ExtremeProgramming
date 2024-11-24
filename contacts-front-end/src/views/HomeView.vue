<template>
  <div class="home">
    <p>我是主页</p>
    <el-table :data="nameList">
        <el-table-column label="姓名" prop="name" ref="name"></el-table-column>
          <el-table-column label="操作">
            <!--操作栏需要template组件传值-->
            <template slot-scope="scope">
              <el-button @click="goToDetails(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
      </el-table>
    <router-view/>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
// import Chart from '../components/Chart.vue'
export default {
  name: 'HomeView',
  components: {
    // Chart,
  },
  data:function(){
      return {
        nameList:[
          {
            id:1,
            name:'你好',
          },
          {
            id:2,
            name:'124',
          }
          
        ],
      };
  },
  methods:{
    goToDetails(row){
      //路由传值
      this.$router.push({
        path: "/details?name=" + row.name + "&id=" + row.id
      });
    }
  }
}

</script>
