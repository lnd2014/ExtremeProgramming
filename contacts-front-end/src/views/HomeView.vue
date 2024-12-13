<template>
  <div class="home">
    <el-card class="box-card">
      <div slot="header" class="card-header">
        <span>通讯录</span>
        <div class="operation-buttons">
          <el-upload
            class="upload-demo"
            action=""
            :before-upload="handleImport"
            accept=".xlsx,.xls"
            :show-file-list="false"
          >
            <el-button 
              type="primary" 
              size="small" 
              icon="el-icon-download"
            >导入联系人</el-button>
          </el-upload>
          <el-button 
            type="success" 
            size="small" 
            icon="el-icon-upload2"
            @click="exportContacts"
          >导出联系人</el-button>
        </div>
      </div>

      <el-table 
        :data="nameList"
        style="width: 100%"
        :stripe="true"
        :border="true"
      >
        <el-table-column label="编号" prop="id" width="80" align="center"></el-table-column>
        <el-table-column label="姓名" prop="name" align="center"></el-table-column>
        <el-table-column label="收藏" width="80" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="toggleFavorite(scope.row)"
            >
              <i :class="scope.row.star ? 'el-icon-star-on' : 'el-icon-star-off'"
                 :style="{ color: scope.row.star ? '#FFB800' : '#909399', fontSize: '20px' }"
              ></i>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button 
              type="primary" 
              size="small" 
              icon="el-icon-view"
              @click="goToDetails(scope.row)"
            >查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <router-view/>
  </div>
</template>


<script>
import * as XLSX from 'xlsx'

export default {
  name: 'HomeView',
  data: function(){
    return {
      nameList:[
        {
          id: 1,
          name: 'A',
          star: true
        },
        {
          id: 2,
          name: 'B',
          star: false
        }
      ],
    };
  },
  methods:{
    goToDetails(row){
      this.$router.push({
        path: "/details?name=" + row.name + "&id=" + row.id
      });
    },
    toggleFavorite(row) {
      const action = row.star ? '取消收藏' : '收藏';
      row.star = !row.star;
      this.$message({
        type: 'success',
        message: `已${action}该联系人`
      });
    },
    exportContacts() {
      const data = this.nameList.map(contact => ({
        姓名: contact.name,
        电话: contact.phone,
        邮箱: contact.email,
        是否收藏: contact.star ? '是' : '否'
      }));
      
      const ws = XLSX.utils.json_to_sheet(data);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, '联系人列表');
      XLSX.writeFile(wb, '联系人.xlsx');
    },
    handleImport(file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const data = e.target.result;
        const workbook = XLSX.read(data, { type: 'array' });
        const firstSheetName = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[firstSheetName];
        const results = XLSX.utils.sheet_to_json(worksheet);
        
        const newContacts = results.map((item, index) => ({
          id: this.nameList.length + index + 1,
          name: item.姓名,
          phone: item.电话,
          email: item.邮箱,
          star: item.是否收藏 === '是'
        }));
        
        this.nameList = [...this.nameList, ...newContacts];
      };
      reader.readAsArrayBuffer(file);
      return false;
    }
  }
}
</script>

<style scoped>
.home {
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

.operation-buttons {
  display: flex;
  gap: 12px;
}

.operation-buttons .el-button {
  padding: 10px 20px;
  border-radius: 4px;
  transition: all 0.3s;
}

.operation-buttons .el-button:hover {
  transform: translateY(-1px);
}

.operation-buttons .el-button [class^="el-icon-"] {
  margin-right: 6px;
}

.el-table {
  margin-top: 15px;
  border-radius: 4px;
  overflow: hidden;
}

/* 表格样式优化 */
:deep(.el-table th) {
  background-color: #f5f7fa;
  color: #303133;
  font-weight: bold;
  padding: 12px 0;
}

:deep(.el-table td) {
  padding: 12px 0;
}

/* 上传按钮样式优化 */
.upload-demo {
  display: inline-block;
}

.upload-demo .el-upload {
  display: block;
}

.upload-demo .el-button {
  box-shadow: 0 2px 4px rgba(0,0,0,.1);
}

.upload-demo .el-upload-list {
  display: none;
}
</style>
