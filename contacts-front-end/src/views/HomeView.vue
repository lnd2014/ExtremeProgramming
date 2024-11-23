<template>
  <div class="home">
    <p>我是主页</p>
    <router-link to="/details">查看详情</router-link>
    <router-view/>
  </div>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {

  }
}
</script>
