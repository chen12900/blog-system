<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>{{ isRegister ? '注册' : '登录' }}</h2>
      <el-form :model="form" label-width="0" @keyup.enter="submit">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="密码"
                    prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item v-if="isRegister">
          <el-input v-model="form.email" placeholder="邮箱（选填）" prefix-icon="Message" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit" :loading="loading" style="width:100%">
            {{ isRegister ? '立即注册' : '立即登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      <p class="toggle">
        {{ isRegister ? '已有账号？' : '没有账号？' }}
        <el-link type="primary" @click="isRegister = !isRegister">
          {{ isRegister ? '去登录' : '去注册' }}
        </el-link>
      </p>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

const router = useRouter()
const isRegister = ref(false)
const loading = ref(false)
const form = reactive({ username: '', password: '', email: '' })

function submit() {
    if (!form.username || !form.password) {
        return ElMessage.warning('请输入用户名和密码')
    }
    loading.value = true
    const url = isRegister.value ? '/user/register' : '/user/login'
    request.post(url, form).then(res => {
        if (!isRegister.value) {
            localStorage.setItem('token', res.data.token)
            localStorage.setItem('username', form.username)
            router.push('/')
        } else {
            ElMessage.success('注册成功，请登录')
            isRegister.value = false
        }
    }).finally(() => {
        loading.value = false
    })
}
</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-card {
    width: 420px;
    padding: 20px 30px;
}
.login-card h2 { text-align: center; margin-bottom: 24px; color: #303133; }
.toggle { text-align: center; color: #909399; font-size: 14px; }
</style>
