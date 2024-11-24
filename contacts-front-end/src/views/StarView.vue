<template>
    <div>
      <p>我是收藏页面</p>
      <el-table :data="nameList">
        <el-table-column label="姓名" prop="name" ref="name"></el-table-column>
        <el-table-column label="操作">
          <!--操作栏需要template组件传值-->
          <template slot-scope="scope">
            <el-button @click="goToDetails(scope.row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
</template>

<script>

export default {
  name: 'StarView',
  components: {

  },
  data:function(){
    return {
      rou:'/details/',
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
    test(row){
      // console.log(scope.row.id);
      console.log(row.name);
    },
    transferMsg(row){
      this.rou+='?name=' + this.row.name + '&id=' + this.row.id;
    },
    //传送到details页面的方法
    goToDetails(row){
      //路由传值
      this.$router.push({
        path: "/details?name=" + row.name + "&id=" + row.id
      });
    }
  }
}
</script>