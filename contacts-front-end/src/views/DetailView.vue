<template>
  <div class="detail-view">
    <el-card class="detail-card">
      <div slot="header" class="card-header">
        <span>联系人详情</span>
        <el-button icon="el-icon-back" type="text" @click="goBack">返回</el-button>
      </div>
      
      <el-form 
        label-width="80px" 
        :model="contactInfo"
        :rules="rules"
        ref="contactForm"
        status-icon
      >
        <el-form-item label="姓名">
          <el-input v-model="contactInfo.name" disabled></el-input>
        </el-form-item>
        
        <el-form-item label="编号">
          <el-input v-model="contactInfo.id" disabled></el-input>
        </el-form-item>
        
        <el-form-item label="电话" prop="phone">
          <el-input 
            v-model="contactInfo.phone"
            prefix-icon="el-icon-phone"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="邮箱" prop="email">
          <el-input 
            v-model="contactInfo.email"
            prefix-icon="el-icon-message"
          ></el-input>
        </el-form-item>
        
        <el-form-item label="收藏">
          <el-switch
            v-model="contactInfo.star"
            active-color="#FFB800"
            inactive-color="#909399"
          </el-switch>
        </el-form-item>
        
        <el-form-item class="form-buttons">
          <el-button type="primary" icon="el-icon-check" @click="saveChanges('contactForm')">保存修改</el-button>
          <el-button icon="el-icon-close" @click="resetForm('contactForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'DetailView',
  data() {
    // 电话验证规则
    const validatePhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入电话号码'));
      } else if (!/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error('请输入正确的手机号码'));
      } else {
        callback();
      }
    };
    
    return {
      contactInfo: {
        id: '',
        name: '',
        phone: '',
        email: '',
        star: false
      },
      rules: {
        phone: [
          { required: true, validator: validatePhone, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 从URL参数获取联系人信息
    const { id, name } = this.$route.query;
    this.contactInfo.id = id;
    this.contactInfo.name = name;
    // 这里需要从父组件或者vuex获取完整的联系人信息
    // TODO: 获取完整联系人信息的逻辑
  },
  methods: {
    goBack() {
      this.$router.push('/');
    },
    saveChanges(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // TODO: 实现保存修改的逻辑
          this.$message({
            type: 'success',
            message: '保存成功！'
          });
        } else {
          this.$message.error('请正确填写表单！');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.detail-view {
  padding: 20px;
  background-color: #f0f2f5;
  min-height: 100vh;
}

.detail-card {
  max-width: 600px;
  margin: 0 auto;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header span {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.form-buttons {
  text-align: center;
  margin-top: 30px;
}

.el-form-item {
  margin-bottom: 22px;
}

.el-input {
  width: 100%;
}
</style>