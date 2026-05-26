<template>
  <div class="detail-container">
    <el-card v-if="article" class="article-content">
      <h1>{{ article.title }}</h1>
      <div class="article-meta">
        <span>浏览量: {{ article.viewCount }}</span>
        <span>{{ formatDate(article.createTime) }}</span>
      </div>
      <el-divider />
      <div class="content" v-html="article.content"></div>
    </el-card>

    <el-card v-if="article" class="comments-section">
      <h3>评论 ({{ comments.length }})</h3>
      <div v-if="isLogin" class="comment-form">
        <el-input v-model="commentContent" type="textarea" :rows="3"
                  placeholder="写下你的评论..." />
        <el-button type="primary" @click="submitComment" style="margin-top:10px;">发表评论</el-button>
      </div>
      <el-empty v-else description="请登录后发表评论" :image-size="80" />

      <div v-for="c in comments" :key="c.id" class="comment-item">
        <div class="comment-meta">
          <strong>用户{{ c.userId }}</strong>
          <span class="comment-time">{{ formatDate(c.createTime) }}</span>
        </div>
        <p>{{ c.content }}</p>
        <el-divider />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import request from '../utils/request'

const route = useRoute()
const article = ref(null)
const comments = ref([])
const commentContent = ref('')
const isLogin = computed(() => !!localStorage.getItem('token'))

onMounted(() => {
    fetchArticle()
    fetchComments()
})

function fetchArticle() {
    request.get(`/article/detail/${route.params.id}`).then(res => {
        article.value = res.data
    })
}

function fetchComments() {
    request.get(`/comment/list/${route.params.id}`).then(res => {
        comments.value = res.data
    })
}

function submitComment() {
    if (!commentContent.value.trim()) return
    request.post('/comment/add', {
        articleId: article.value.id,
        content: commentContent.value
    }).then(() => {
        commentContent.value = ''
        fetchComments()
    })
}

function formatDate(dateStr) {
    if (!dateStr) return ''
    return new Date(dateStr).toLocaleDateString('zh-CN')
}
</script>

<style scoped>
.detail-container { max-width: 900px; margin: 0 auto; padding: 20px 0; }
.article-content h1 { font-size: 28px; margin-bottom: 12px; }
.article-meta { color: #909399; font-size: 13px; display: flex; gap: 20px; margin-bottom: 8px; }
.content { line-height: 1.9; font-size: 16px; color: #303133; }
.comments-section { margin-top: 20px; }
.comment-form { margin: 16px 0; }
.comment-item { margin-bottom: 8px; }
.comment-meta { display: flex; justify-content: space-between; color: #606266; font-size: 14px; }
.comment-time { color: #c0c4cc; font-size: 12px; }
.comment-item p { margin-top: 6px; color: #303133; }
</style>
