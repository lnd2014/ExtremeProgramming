<template>
    <div>
        <el-button type="primary" icon="el-icon-back" @click="backToHome">返回主页</el-button>
        <p>我是联系人详情页面</p>
        <!--用query接收传进来的值-->
        <p>姓名:{{ this.$route.query.name }}</p>
        <el-table :data="phoneList">
          <el-table-column label="电话号码" prop="phone"></el-table-column>
        </el-table>
        <el-table :data="addressList">
          <el-table-column label="邮箱" prop="address"></el-table-column>
        </el-table>
        
    </div>
</template>

<script>


export default{
    name:'myDetails',
    methods:{
        backToHome(){
            this.$router.go(-1);
        },
        test(){
            alert("Sucessful");
        }
    },
    data:function(){
        return {
            phoneList:[
                {
                    uid:'1',
                    phone:123123,
                    id:'1'
                },
                {
                    uid:'2',
                    phone:123123123,
                    id:'12'
                }
            ],
            addressList:[
                {
                    uid:'1',
                    address:'wisoah@009.com',
                    id:'1'
                },
               
            ],
            myId:this.$router.query,
        };
    },

}
</script>