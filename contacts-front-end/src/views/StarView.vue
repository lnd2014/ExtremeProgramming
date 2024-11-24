<template>
  <div class="star-view">
    <el-card class="box-card">
      <div slot="header" class="card-header">
        <span>收藏联系人</span>
      </div>

      <el-table 
        :data="starredContacts"
        style="width: 100%"
        :stripe="true"
        :border="true"
        v-loading="loading"
      >
        <el-table-column label="编号" prop="id" width="80" align="center"></el-table-column>
        <el-table-column label="姓名" prop="name" align="center"></el-table-column>
        <el-table-column label="收藏" width="80" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="cancelStar(scope.row)"
            >
              <i class="el-icon-star-on"
                 style="color: #FFB800; font-size: 20px"
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

      <div class="empty-block" v-if="starredContacts.length === 0">
        <el-empty description="暂无收藏联系人"></el-empty>
      </div>
    </el-card>
  </div>
</template>


<script>
export default {
  name: 'StarView',
  data() {
    return {
      loading: false,
      starredContacts: []
    }
  },
  created() {
    // 获取收藏的联系人列表
    this.getStarredContacts();
  },
  methods: {
    getStarredContacts() {
      this.loading = true;
      // 从父组件或Vuex获取联系人列表
      const allContacts = this.$parent.nameList || [];
      this.starredContacts = allContacts.filter(contact => contact.star);
      this.loading = false;
    },
    cancelStar(row) {
      this.$confirm('确定取消收藏该联系人吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 更新父组件中的数据
        const contact = this.$parent.nameList.find(item => item.id === row.id);
        if (contact) {
          contact.star = false;
          // 更新当前视图的数据
          this.getStarredContacts();
          this.$message({
            type: 'success',
            message: '已取消收藏'
          });
        }
      }).catch(() => {
        // 取消操作
      });
    },
    goToDetails(row) {
      this.$router.push({
        path: "/details?name=" + row.name + "&id=" + row.id
      });
    }
  }
}
</script>

<style scoped>
.star-view {
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

/* 表格样式优化 */
:deep(.el-table) {
  margin-top: 15px;
  border-radius: 4px;
  overflow: hidden;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
  color: #303133;
  font-weight: bold;
  padding: 12px 0;
}

:deep(.el-table td) {
  padding: 12px 0;
}

.empty-block {
  margin: 30px 0;
  text-align: center;
}

:deep(.el-button--text) {
  padding: 8px;
  transition: all 0.3s;
}

:deep(.el-button--text:hover) {
  background-color: rgba(64, 158, 255, 0.1);
  border-radius: 4px;
}
</style>
