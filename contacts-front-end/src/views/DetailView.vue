<template>
  <div class="detail-view">
    <el-card class="box-card">
      <div slot="header" class="card-header">
        <span>联系人详情</span>
        <el-button type="text" icon="el-icon-back" @click="goBack">返回</el-button>
      </div>

      <el-descriptions :column="1" border>
        <el-descriptions-item label="姓名">{{ contact.name }}</el-descriptions-item>
        
        <!-- 电话号码区域 -->
        <el-descriptions-item label="电话号码">
          <div v-for="(phone, index) in contact.phones" :key="'phone'+index" class="info-item">
            <span>{{ phone }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('phone', index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('phone', index)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('phone')">添加电话</el-button>
        </el-descriptions-item>

        <!-- 电子邮件区域 -->
        <el-descriptions-item label="电子邮件">
          <div v-for="(email, index) in contact.emails" :key="'email'+index" class="info-item">
            <span>{{ email }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('email', index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('email', index)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('email')">添加邮箱</el-button>
        </el-descriptions-item>

        <!-- QQ区域 -->
        <el-descriptions-item label="QQ">
          <div v-for="(qq, index) in contact.qqs" :key="'qq'+index" class="info-item">
            <span>{{ qq }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('qq', index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('qq', index)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('qq')">添加QQ</el-button>
        </el-descriptions-item>

        <!-- 地址区域 -->
        <el-descriptions-item label="地址">
          <div v-for="(address, index) in contact.addresses" :key="'address'+index" class="info-item">
            <span>{{ address }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('address', index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('address', index)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('address')">添加地址</el-button>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 添加/编辑信息的对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="30%">
      <el-input v-model="editingValue" :placeholder="placeholderText"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'DetailView',
  data() {
    return {
      contact: {
        id: '',
        name: '',
        phones: [],
        emails: [],
        qqs: [],
        addresses: []
      },
      dialogVisible: false,
      dialogTitle: '',
      editingValue: '',
      editingType: '',
      editingIndex: -1,
      placeholderText: ''
    }
  },
  created() {
    const { id, name } = this.$route.query;
    this.contact.id = id;
    this.contact.name = name;
    this.getContactDetails();
  },
  methods: {
    getContactDetails() {
      // 从父组件或Vuex获取联系人详细信息
      const contact = this.$parent.nameList.find(item => item.id === this.contact.id);
      if (contact) {
        this.contact = { ...contact };
      }
    },
    goBack() {
      this.$router.go(-1);
    },
    addInfo(type) {
      this.editingType = type;
      this.editingIndex = -1;
      this.editingValue = '';
      this.dialogTitle = `添加${this.getTypeLabel(type)}`;
      this.placeholderText = `请输入${this.getTypeLabel(type)}`;
      this.dialogVisible = true;
    },
    editInfo(type, index) {
      this.editingType = type;
      this.editingIndex = index;
      this.editingValue = this.contact[type + 's'][index];
      this.dialogTitle = `编辑${this.getTypeLabel(type)}`;
      this.placeholderText = `请输入${this.getTypeLabel(type)}`;
      this.dialogVisible = true;
    },
    deleteInfo(type, index) {
      this.$confirm(`确定删除该${this.getTypeLabel(type)}吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.contact[type + 's'].splice(index, 1);
        this.updateContact();
      });
    },
    saveInfo() {
      if (!this.editingValue.trim()) {
        this.$message.warning('输入内容不能为空');
        return;
      }

      const arrayName = this.editingType + 's';
      if (this.editingIndex === -1) {
        // 添加新信息
        this.contact[arrayName].push(this.editingValue);
      } else {
        // 编辑现有信息
        this.contact[arrayName][this.editingIndex] = this.editingValue;
      }

      this.updateContact();
      this.dialogVisible = false;
    },
    updateContact() {
      // 更新父组件中的数据
      const index = this.$parent.nameList.findIndex(item => item.id === this.contact.id);
      if (index !== -1) {
        this.$parent.nameList[index] = { ...this.contact };
      }
      this.$message.success('更新成功');
    },
    getTypeLabel(type) {
      const labels = {
        phone: '电话号码',
        email: '电子邮件',
        qq: 'QQ',
        address: '地址'
      };
      return labels[type];
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

.box-card {
  margin-bottom: 20px;
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

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 0;
  border-bottom: 1px solid #EBEEF5;
}

.info-item:last-child {
  border-bottom: none;
}

.operations {
  display: flex;
  gap: 10px;
}
</style>