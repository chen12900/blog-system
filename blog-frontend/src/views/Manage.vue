<template>
  <div class="manage-container">
    <el-card>
      <div class="manage-header">
        <h2>文章管理</h2>
        <el-button type="primary" @click="$router.push('/edit')">发布文章</el-button>
      </div>

      <el-table :data="articles" stripe border style="margin-top:20px;">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column label="浏览量" width="80">
          <template #default="{ row }">{{ row.viewCount }}</template>
        </el-table-column>
        <el-table-column label="创建时间" width="160">
          <template #default="{ row }">{{ formatDate(row.createTime) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="$router.push(`/edit/${row.id}`)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top:20px;" v-if="total > 0">
        <el-pagination background layout="prev, pager, next" :total="total"
                       :page-size="pageSize" v-model:current-page="pageNum"
                       @current-change="fetchArticles" />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '../utils/request'

const articles = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

onMounted(() => fetchArticles())

function fetchArticles() {
    request.get('/article/list', {
        params: { pageNum: pageNum.value, pageSize: pageSize.value }
    }).then(res => {
        articles.value = res.data.records
        total.value = res.data.total
    })
}

function handleDelete(id) {
    ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        request.delete(`/article/delete/${id}`).then(() => {
            ElMessage.success('删除成功')
            fetchArticles()
        })
    })
}

function formatDate(dateStr) {
    if (!dateStr) return ''
    return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.manage-container { max-width: 1100px; margin: 0 auto; padding: 20px 0; }
.manage-header { display: flex; justify-content: space-between; align-items: center; }
</style>
