import Vue from 'vue'
import App from './App.vue'
import router from './router'
//引入elementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
//使用elementUI
Vue.use(ElementUI);

new Vue({
  //将app添加到el控件中间
  el:'#app',
  router,
  render: h => h(App)
}).$mount('#app')
