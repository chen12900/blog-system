<template>
  <div class="home-container">
    <el-row :gutter="20">
      <el-col :span="18">
        <div class="article-list">
          <el-card v-for="article in articles" :key="article.id" class="article-card"
                   shadow="hover" @click="$router.push(`/article/${article.id}`)">
            <h3>{{ article.title }}</h3>
            <p class="preview">{{ stripHtml(article.content).substring(0, 150) }}...</p>
            <div class="meta">
              <span><el-icon><View /></el-icon> {{ article.viewCount || 0 }}</span>
              <span>{{ formatDate(article.createTime) }}</span>
            </div>
          </el-card>
        </div>
        <div class="pagination" v-if="total > 0">
          <el-pagination background layout="prev, pager, next" :total="total"
                         :page-size="pageSize" v-model:current-page="pageNum"
                         @current-change="fetchArticles" />
        </div>
        <el-empty v-if="total === 0" description="暂无文章" />
      </el-col>

      <el-col :span="6">
        <el-card class="sidebar">
          <h4>文章分类</h4>
          <el-menu>
            <el-menu-item @click="selectedCategory = null; fetchArticles()">
              <span :class="{ active: !selectedCategory }">全部分类</span>
            </el-menu-item>
            <el-menu-item v-for="cat in categories" :key="cat.id"
                          @click="selectedCategory = cat.id; fetchArticles()">
              <span :class="{ active: selectedCategory === cat.id }">{{ cat.categoryName }}</span>
            </el-menu-item>
          </el-menu>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../utils/request'

const articles = ref([])
const categories = ref([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const selectedCategory = ref(null)

onMounted(() => {
    fetchArticles()
    fetchCategories()
})

function fetchArticles() {
    request.get('/article/list', {
        params: { pageNum: pageNum.value, pageSize: pageSize.value, categoryId: selectedCategory.value }
    }).then(res => {
        articles.value = res.data.records
        total.value = res.data.total
    })
}

function fetchCategories() {
    request.get('/category/list').then(res => {
        categories.value = res.data
    })
}

function stripHtml(html) {
    return html ? html.replace(/<[^>]+>/g, '') : ''
}

function formatDate(dateStr) {
    if (!dateStr) return ''
    return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.home-container { max-width: 1200px; margin: 0 auto; padding: 20px 0; }
.article-card { margin-bottom: 16px; cursor: pointer; }
.article-card h3 { margin-bottom: 8px; color: #303133; }
.preview { color: #909399; font-size: 14px; line-height: 1.6; }
.meta { margin-top: 12px; color: #c0c4cc; font-size: 13px; display: flex; gap: 20px; align-items: center; }
.meta .el-icon { vertical-align: middle; }
.pagination { margin-top: 20px; display: flex; justify-content: center; }
.sidebar h4 { margin-bottom: 12px; font-size: 16px; }
.sidebar .active { color: #409eff; font-weight: bold; }
</style>
