<template>
  <div class="edit-container">
    <el-card>
      <h2>{{ isEdit ? '编辑文章' : '发布文章' }}</h2>
      <el-form :model="form" label-width="80px" style="margin-top:20px;">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入文章标题" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width:200px;">
            <el-option v-for="cat in categories" :key="cat.id"
                       :label="cat.categoryName" :value="cat.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容">
          <div style="border:1px solid #dcdfe6; border-radius:4px;">
            <el-input v-model="form.content" type="textarea" :rows="20"
                      placeholder="支持 HTML 格式内容..." />
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save" :loading="saving">保存</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

const route = useRoute()
const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const saving = ref(false)
const categories = ref([])
const form = reactive({ title: '', content: '', categoryId: null })

onMounted(() => {
    fetchCategories()
    if (isEdit.value) {
        request.get(`/article/detail/${route.params.id}`).then(res => {
            const a = res.data
            form.title = a.title
            form.content = a.content
            form.categoryId = a.categoryId
        })
    }
})

function fetchCategories() {
    request.get('/category/list').then(res => {
        categories.value = res.data
    })
}

function save() {
    if (!form.title || !form.content) {
        return ElMessage.warning('请填写标题和内容')
    }
    saving.value = true
    const api = isEdit.value
        ? request.put('/article/update', { ...form, id: Number(route.params.id) })
        : request.post('/article/create', form)

    api.then(() => {
        ElMessage.success(isEdit.value ? '更新成功' : '发布成功')
        router.push('/manage')
    }).finally(() => { saving.value = false })
}
</script>

<style scoped>
.edit-container { max-width: 960px; margin: 0 auto; padding: 20px 0; }
</style>
