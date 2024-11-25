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
            <span>{{ phone.number }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('phone', phone, index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('phone', phone)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('phone')">添加电话</el-button>
        </el-descriptions-item>

        <!-- 电子邮件区域
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

        QQ区域
        <el-descriptions-item label="QQ">
          <div v-for="(qq, index) in contact.qqs" :key="'qq'+index" class="info-item">
            <span>{{ qq }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('qq', index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('qq', index)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('qq')">添加QQ</el-button>
        </el-descriptions-item>-->

        <!-- 地址区域
        <el-descriptions-item label="地址">
          <div v-for="(address, index) in contact.addresses" :key="'address'+index" class="info-item">
            <span>{{ address }}</span>
            <div class="operations">
              <el-button type="text" icon="el-icon-edit" @click="editInfo('address', index)">编辑</el-button>
              <el-button type="text" icon="el-icon-delete" @click="deleteInfo('address', index)">删除</el-button>
            </div>
          </div>
          <el-button type="text" icon="el-icon-plus" @click="addInfo('address')">添加地址</el-button>
        </el-descriptions-item>-->
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
import request from '@/utils/request'
export default {
  name: 'DetailView',
  data() {
    return {
      contact: {
        id: '',
        name: '',
        phones: [{
          id:'',
          uid:'',
          number:''
        }],
   
      },
      dialogVisible: false,
      dialogTitle: '',
      editingValue: '',
      editingType: '',
      editingIndex: -1,
      placeholderText: '',
      editingPhone:{
        id:'',
        uid:'',
        number:''
      }
    }
  },
  created() {
    //从路由处获取该页面的id和名称
    const { id, name } = this.$route.query;
    this.contact.id = id;
    this.contact.name = name;
    //去后端获取电话表
    this.load(id);
  },
  methods: {
    //加载
    load() {
      var path = 'details/' +this.contact.id; 
      request.get(path).then(
        res=>{
          this.contact.phones = res;
        }
      )
      //刷新页面时重置信息
      this.editingPhone = {
        id:'',
        uid:'',
        number:''
      };
    },
    //更新值后刷新页面
    update(phone){
      var id = phone.id;
      console.log(id);
      var path = '/update-number/' + id;
      request.put(path,phone).then(
        res => {
          this.load();
        }
      )
    },
    goBack() {
      this.$router.go(-1);
    },
    addInfo(type) {
      this.editingType = type;
      if(this.editingType=='phone'){
        this.editingPhone.uid = this.contact.id;
      }
      this.editingIndex = -1;
      this.editingValue = '';
      this.dialogTitle = `添加${this.getTypeLabel(type)}`;
      this.placeholderText = `请输入${this.getTypeLabel(type)}`;
      this.dialogVisible = true;
    },
    editInfo(type, phone,index) {
      this.editingType = type;
      if(this.editingType=='phone'){
        //传入当前行的值
        this.editingPhone=phone;
      }
      //由于v-for顺序渲染,显示的index实际上是contact数组里的坐标
      this.editingIndex = index;
      this.editingValue = this.contact[type + 's'][index].number;
      this.dialogTitle = `编辑${this.getTypeLabel(type)}`;
      this.placeholderText = `请输入${this.getTypeLabel(type)}`;
      this.dialogVisible = true;
    },
    deleteInfo(type, phone) {
      var id = phone.id;
      this.$confirm(`确定删除该${this.getTypeLabel(type)}吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //js里需要访问对象记得自定义路径,不支持直接在路径里写表达式
        var path = 'details/' + id;
        request.delete(path).then(
          res => {
            this.load();
          }
        )
      });
    },
    saveInfo() {
      if (!this.editingValue.trim()) {
        this.$message.warning('输入内容不能为空');
        return;
      }

      // const arrayName = this.editingType + 's';
      this.editingPhone.number=this.editingValue;
      if (this.editingIndex === -1) {
        // 添加新信息
        console.log(this.editingPhone);
        request.post('/create-phone',this.editingPhone).then(
          res=>{
            this.load();
          }
        );
      } else {
        // 编辑现有信息
        this.update(this.editingPhone);
      }

      
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
  border-radius: 8px;
  overflow: hidden;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background-color: #fff;
  border-bottom: 1px solid #ebeef5;
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
  padding: 12px 0;
  border-bottom: 1px solid #EBEEF5;
  transition: background-color 0.3s;
}

.info-item:hover {
  background-color: #f5f7fa;
}

.info-item:last-child {
  border-bottom: none;
}

.operations {
  display: flex;
  gap: 12px;
  opacity: 0.7;
  transition: opacity 0.3s;
}

.info-item:hover .operations {
  opacity: 1;
}

/* 描述列表样式优化 */
:deep(.el-descriptions) {
  padding: 20px;
}

:deep(.el-descriptions__label) {
  width: 120px;
  color: #606266;
  font-weight: bold;
  background-color: #f5f7fa;
}

:deep(.el-descriptions__content) {
  padding: 12px 15px;
}

:deep(.el-button--text) {
  padding: 0 8px;
  font-size: 14px;
}

:deep(.el-button--text:hover) {
  color: #409EFF;
  background-color: rgba(64, 158, 255, 0.1);
  border-radius: 4px;
}

/* 对话框样式优化 */
:deep(.el-dialog) {
  border-radius: 8px;
}

:deep(.el-dialog__header) {
  padding: 20px;
  border-bottom: 1px solid #ebeef5;
}

:deep(.el-dialog__body) {
  padding: 30px 20px;
}

:deep(.el-dialog__footer) {
  padding: 15px 20px;
  border-top: 1px solid #ebeef5;
}
</style>
