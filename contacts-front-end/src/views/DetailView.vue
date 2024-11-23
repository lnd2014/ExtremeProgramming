<template>
    <div>
        <el-button type="primary" icon="el-icon-back" @click="backToHome">返回主页</el-button>
        <p>我是联系人详情页面</p>
        
    </div>
</template>

<script>
export default{
    name:'details',
    methods:{
        backToHome(){
            this.$router.go(-1);
        },
        test(){
            alert("Sucessful");
        }
    }
}
</script>