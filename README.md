二、个人博客系统（Spring Boot + Vue）

技术栈

· 后端：Spring Boot、MyBatis-Plus、JWT、BCrypt、MySQL
· 前端：Vue 3、Element Plus、Axios
· 工具：Git、Maven、Postman（接口测试）

编程过程与详细说明

1. 项目整体设计

· 采用前后端分离架构。
· 后端提供 RESTful API，前端通过 Axios 调用。
· 实现文章发布、编辑、删除、分类管理、评论互动功能。

2. 后端开发（Spring Boot）

2.1 项目初始化

· Spring Boot 项目集成 MyBatis-Plus、MySQL Driver、JWT、BCrypt 等依赖。
· 配置 application.yml：数据库连接、MyBatis-Plus 分页插件。

2.2 数据库设计

· 用户表（id、用户名、密码、邮箱）
· 文章表（id、标题、内容、分类id、创建时间、更新时间）
· 分类表（id、分类名）
· 评论表（id、文章id、用户id、内容、时间）

2.3 用户认证与权限控制

· 登录接口：验证用户名+密码（BCrypt 比对）。
· 登录成功后生成 JWT（含用户id、角色），返回给前端。
· 自定义拦截器：除登录、注册外，所有请求需校验 JWT 有效性。

2.4 文章管理

· 使用 MyBatis-Plus 实现文章 CRUD。
· 分页查询：配合 Page 对象和分页插件。
· 文章列表按时间倒序，支持按分类筛选。

2.5 安全与加密

· 密码存储：用户注册时使用 BCrypt 加密，避免明文存储。
· 密码校验：登录时对输入密码进行 BCrypt 比对。

3. 前端开发（Vue 3 + Element Plus）

3.1 项目初始化

· 创建 Vue 3 项目，安装 Element Plus、Axios、Vue Router。

3.2 路由与布局

· 设置路由：首页（文章列表）、文章详情、个人中心、登录/注册。
· 后台管理布局：侧边栏 + 头部 + 内容区。

3.3 Axios 封装与 Token 管理

· 封装 request.js：统一请求地址，添加请求拦截器自动注入 JWT。
· 响应拦截器处理 401 未授权，自动跳转登录。

3.4 文章列表与详情页

· 文章列表：使用 Element Plus 卡片布局，支持分页。
· 文章详情：显示标题、内容、评论区域，用户可发表评论。

3.5 发布/编辑文章

· 使用 Element Plus 富文本编辑器（如 el-editor）录入内容。
· 表单校验后调用后端接口保存。

3.6 评论互动

· 展示评论列表，用户登录后可发表评论。
· 评论提交后刷新列表。
