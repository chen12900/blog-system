import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        component: () => import('../views/Layout.vue'),
        children: [
            { path: '', name: 'Home', component: () => import('../views/Home.vue') },
            { path: 'article/:id', name: 'ArticleDetail', component: () => import('../views/ArticleDetail.vue') },
            { path: 'manage', name: 'Manage', component: () => import('../views/Manage.vue'), meta: { requiresAuth: true } },
            { path: 'edit', name: 'ArticleEdit', component: () => import('../views/ArticleEdit.vue'), meta: { requiresAuth: true } },
            { path: 'edit/:id', name: 'ArticleEditId', component: () => import('../views/ArticleEdit.vue'), meta: { requiresAuth: true } },
        ]
    },
    { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth && !localStorage.getItem('token')) {
        next('/login')
    } else {
        next()
    }
})

export default router
