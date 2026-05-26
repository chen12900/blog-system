<template>
  <el-container class="layout">
    <el-header class="header">
      <div class="header-left">
        <h2 @click="$router.push('/')" style="cursor:pointer;">📝 个人博客</h2>
        <el-menu mode="horizontal" :default-active="activeMenu" router
                 background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/manage" v-if="isLogin">后台管理</el-menu-item>
        </el-menu>
      </div>
      <div class="header-right">
        <template v-if="isLogin">
          <span class="username">{{ username }}</span>
          <el-button type="danger" size="small" @click="logout">退出</el-button>
        </template>
        <template v-else>
          <el-button type="primary" size="small" @click="$router.push('/login')">登录</el-button>
        </template>
      </div>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const isLogin = computed(() => !!localStorage.getItem('token'))
const username = computed(() => localStorage.getItem('username') || '')
const activeMenu = computed(() => '/' + (route.path.split('/')[1] || ''))

function logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    router.push('/')
}
</script>

<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body { background: #f0f2f5; }

.layout { min-height: 100vh; }
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #545c64;
    padding: 0 24px;
}
.header-left { display: flex; align-items: center; gap: 32px; }
.header-left h2 { color: #fff; font-size: 20px; white-space: nowrap; }
.header-right { display: flex; align-items: center; gap: 12px; }
.username { color: #e0e0e0; font-size: 14px; }

.el-menu--horizontal { border-bottom: none !important; }
.el-menu-item { height: 60px; line-height: 60px; }

@media (max-width: 768px) {
    .header-left h2 { display: none; }
    .header { padding: 0 12px; }
}
</style>
