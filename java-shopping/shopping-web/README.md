<div align="center">

# 🎨 Shopping Web

<img src="https://img.shields.io/badge/Vue-3.4+-4FC08D?style=for-the-badge&logo=vue.js" alt="Vue Version">
<img src="https://img.shields.io/badge/TypeScript-5.3+-3178C6?style=for-the-badge&logo=typescript" alt="TypeScript">
<img src="https://img.shields.io/badge/Vite-5.0+-646CFF?style=for-the-badge&logo=vite" alt="Vite">
<img src="https://img.shields.io/badge/Element_Plus-2.5+-409EFF?style=for-the-badge" alt="Element Plus">
<img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="License">

**现代化电商前端 | Vue 3 + TypeScript | 组件化架构**

[快速开始](#-快速开始) · [功能特性](#-功能特性) · [组件文档](#-组件文档)

---

</div>

## ✨ 特性

<table>
<tr>
<td width="50%">

### 🚀 技术亮点

- **Vue 3 Composition API**
- **TypeScript 类型安全**
- **Vite 极速构建**
- **Pinia 状态管理**
- **自动导入组件**

</td>
<td width="50%">

### 🎯 功能特性

- **响应式设计**
- **暗黑模式支持**
- **国际化 (i18n)**
- **权限管理**
- **状态持久化**

</td>
</tr>
</table>

---

## 📁 项目结构

```
shopping-web/
├── 📂 public/                      # 静态资源
│
├── 📂 src/
│   ├── 📂 api/                     # 📡 API 接口
│   │   ├── 📄 user.ts              # 用户接口
│   │   ├── 📄 product.ts           # 商品接口
│   │   ├── 📄 cart.ts              # 购物车接口
│   │   └── 📄 order.ts             # 订单接口
│   │
│   ├── 📂 components/              # 🧩 公共组件
│   │   └── 📄 ProductCard.vue      # 商品卡片
│   │
│   ├── 📂 layouts/                 # 📐 布局组件
│   │   └── 📄 MainLayout.vue       # 主布局
│   │
│   ├── 📂 router/                  # 🛤️ 路由配置
│   │   └── 📄 index.ts
│   │
│   ├── 📂 stores/                  # 📦 状态管理
│   │   └── 📄 user.ts              # 用户状态
│   │
│   ├── 📂 styles/                  # 🎨 样式文件
│   │   ├── 📄 index.scss           # 主样式
│   │   └── 📄 variables.scss       # 变量定义
│   │
│   ├── 📂 types/                   # 📝 类型定义
│   │   ├── 📄 user.ts
│   │   ├── 📄 product.ts
│   │   ├── 📄 cart.ts
│   │   └── 📄 order.ts
│   │
│   ├── 📂 utils/                   # 🔧 工具函数
│   │   └── 📄 request.ts           # Axios 封装
│   │
│   ├── 📂 views/                   # 📄 页面组件
│   │   ├── 📂 auth/                # 认证页面
│   │   │   ├── 📄 login.vue
│   │   │   └── 📄 register.vue
│   │   ├── 📂 home/                # 首页
│   │   ├── 📂 product/             # 商品页面
│   │   │   ├── 📄 detail.vue
│   │   │   ├── 📄 list.vue
│   │   │   └── 📄 search.vue
│   │   ├── 📂 cart/                # 购物车
│   │   ├── 📂 order/               # 订单页面
│   │   │   ├── 📄 index.vue
│   │   │   ├── 📄 detail.vue
│   │   │   └── 📄 checkout.vue
│   │   ├── 📂 user/                # 用户中心
│   │   └── 📂 error/               # 错误页面
│   │
│   ├── 📄 App.vue                  # 根组件
│   └── 📄 main.ts                  # 入口文件
│
├── 📄 index.html
├── 📄 package.json
├── 📄 tsconfig.json
├── 📄 tsconfig.node.json
└── 📄 vite.config.ts
```

---

## 🛠️ 技术栈

### 核心框架

| 技术 | 版本 | 说明 |
|:-----|:-----|:-----|
| <img src="https://img.shields.io/badge/Vue-4FC08D?style=flat-square&logo=vue.js&logoColor=white" alt="Vue"> Vue | 3.4.15 | 渐进式框架 |
| <img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white" alt="TypeScript"> TypeScript | 5.3.3 | 类型支持 |
| <img src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=vite&logoColor=white" alt="Vite"> Vite | 5.0.11 | 构建工具 |

### 状态 & 路由

| 技术 | 版本 | 说明 |
|:-----|:-----|:-----|
| <img src="https://img.shields.io/badge/Pinia-F7D336?style=flat-square" alt="Pinia"> Pinia | 2.1.7 | 状态管理 |
| <img src="https://img.shields.io/badge/Vue_Router-4FC08D?style=flat-square&logo=vue.js&logoColor=white" alt="Vue Router"> Vue Router | 4.2.5 | 路由管理 |

### UI & 样式

| 技术 | 版本 | 说明 |
|:-----|:-----|:-----|
| <img src="https://img.shields.io/badge/Element_Plus-409EFF?style=flat-square" alt="Element Plus"> Element Plus | 2.5.3 | UI 组件库 |
| <img src="https://img.shields.io/badge/Sass-CC6699?style=flat-square&logo=sass&logoColor=white" alt="Sass"> Sass | 1.70.0 | CSS 预处理器 |

### 工具库

| 技术 | 版本 | 说明 |
|:-----|:-----|:-----|
| <img src="https://img.shields.io/badge/Axios-5A29E4?style=flat-square&logo=axios&logoColor=white" alt="Axios"> Axios | 1.6.5 | HTTP 客户端 |
| <img src="https://img.shields.io/badge/VueUse-35495E?style=flat-square" alt="VueUse"> VueUse | 10.7.2 | Composition 工具 |
| <img src="https://img.shields.io/badge/Day.js-E05E00?style=flat-square" alt="Day.js"> Day.js | 1.11.10 | 日期处理 |

---

## 🚀 快速开始

### 环境要求

| 组件 | 版本要求 |
|------|----------|
| <img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=node.js&logoColor=white" alt="Node.js"> Node.js | 18+ |
| <img src="https://img.shields.io/badge/npm-CB3837?style=flat-square&logo=npm&logoColor=white" alt="npm"> npm | 9+ |

### 安装运行

```bash
# 📥 进入项目目录
cd shopping-web

# 📦 安装依赖
npm install

# 🚀 启动开发服务器
npm run dev

# 🌐 访问 http://localhost:3000
```

### 构建部署

```bash
# 🔨 构建生产版本
npm run build

# 👀 预览生产版本
npm run preview
```

---

## 📖 页面路由

| 路由 | 页面 | 说明 | 认证 |
|:-----|:-----|:-----|:----:|
| `/` | 🏠 首页 | 商品展示 | ❌ |
| `/product/:id` | 📦 商品详情 | 商品信息 | ❌ |
| `/category/:id` | 📋 商品列表 | 分类商品 | ❌ |
| `/search` | 🔍 搜索结果 | 商品搜索 | ❌ |
| `/cart` | 🛒 购物车 | 购物车管理 | ✅ |
| `/order` | 📑 订单列表 | 我的订单 | ✅ |
| `/order/:id` | 📄 订单详情 | 订单信息 | ✅ |
| `/checkout` | 💳 结算 | 订单结算 | ✅ |
| `/user` | 👤 个人中心 | 用户信息 | ✅ |
| `/login` | 🔐 登录 | 用户登录 | ❌ |
| `/register` | 📝 注册 | 用户注册 | ❌ |

---

## 🎯 功能特性

<details>
<summary><b>🔐 用户认证</b></summary>

- JWT Token 认证
- 自动刷新 Token
- 登录状态持久化
- 路由权限守卫

```typescript
// 路由守卫示例
router.beforeEach((to, _from, next) => {
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
  } else {
    next()
  }
})
```

</details>

<details>
<summary><b>📦 商品功能</b></summary>

- 商品列表展示
- 商品详情查看
- 分类筛选
- 关键词搜索
- 热门/新品推荐

</details>

<details>
<summary><b>🛒 购物车</b></summary>

- 添加商品
- 修改数量
- 删除商品
- 全选/取消全选
- 价格计算

</details>

<details>
<summary><b>📋 订单功能</b></summary>

- 创建订单
- 订单支付
- 取消订单
- 确认收货
- 订单列表
- 订单详情

</details>

---

## 🧩 组件文档

### 商品卡片组件

```vue
<template>
  <ProductCard 
    :product="product" 
    @click="goToDetail"
  />
</template>

<script setup lang="ts">
import ProductCard from '@/components/ProductCard.vue'
import type { Product } from '@/types/product'

const product = ref<Product>({
  id: 1,
  name: '商品名称',
  price: 99.99,
  mainImage: '/image.jpg'
})
</script>
```

### 自动导入

配置了 `unplugin-auto-import` 和 `unplugin-vue-components`，以下无需手动导入：

**Vue API**
```typescript
// 自动导入
ref, reactive, computed, watch, onMounted, ...
```

**Vue Router**
```typescript
// 自动导入
useRouter, useRoute, onBeforeRouteLeave, ...
```

**Pinia**
```typescript
// 自动导入
defineStore, storeToRefs, ...
```

**Element Plus 组件**
```vue
<!-- 自动注册 -->
<el-button>按钮</el-button>
<el-input v-model="value" />
```

---

## 📝 类型定义

### 用户类型

```typescript
interface UserInfo {
  id: number
  username: string
  nickname: string
  email: string
  phone: string
  avatar: string
}

interface LoginRequest {
  username: string
  password: string
}

interface LoginResponse {
  accessToken: string
  refreshToken: string
  expiresIn: number
  user: UserInfo
}
```

### 商品类型

```typescript
interface Product {
  id: number
  name: string
  productCode: string
  categoryId: number
  brandId: number
  mainImage: string
  price: number
  originalPrice: number
  stock: number
  sales: number
  status: number
  brief: string
}

interface ProductListParams {
  pageNum?: number
  pageSize?: number
  categoryId?: number
  keyword?: string
}
```

### 订单类型

```typescript
interface Order {
  id: number
  orderNo: string
  userId: number
  totalAmount: number
  status: number
  receiverName: string
  receiverPhone: string
  receiverAddress: string
  items: OrderItem[]
  createdAt: string
}

interface OrderCreate {
  userId: number
  items: OrderItemInput[]
  receiverName: string
  receiverPhone: string
  receiverAddress: string
}
```

---

## ⚙️ 配置说明

### Vite 配置

```typescript
// vite.config.ts
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      imports: ['vue', 'vue-router', 'pinia'],
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
```

### API 代理

开发环境 API 代理配置：

```yaml
/api → http://localhost:8080/api
```

---

## 📐 开发规范

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 组件文件 | PascalCase | `ProductCard.vue` |
| 页面文件 | kebab-case | `product-detail.vue` |
| 变量/函数 | camelCase | `getUserInfo` |
| 常量 | UPPER_SNAKE_CASE | `API_BASE_URL` |
| 类型/接口 | PascalCase | `UserInfo` |

### 目录规范

```
📂 api/        → API 接口定义
📂 components/ → 可复用组件
📂 views/      → 页面组件
📂 stores/     → Pinia 状态
📂 types/      → TypeScript 类型
📂 utils/      → 工具函数
📂 styles/     → 全局样式
```

### Git 提交规范

| 类型 | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | 修复 Bug |
| `docs` | 文档更新 |
| `style` | 代码格式 |
| `refactor` | 重构 |
| `test` | 测试 |
| `chore` | 构建/工具 |

---

## 📈 性能优化

- ⚡ **路由懒加载**: 按需加载页面组件
- 📦 **组件自动导入**: 减少打包体积
- 🗜️ **Gzip 压缩**: 生产环境压缩
- 🖼️ **图片懒加载**: 优化加载速度
- 💾 **状态持久化**: Pinia 持久化存储

---

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. 🍴 Fork 本仓库
2. 🌿 创建分支 (`git checkout -b feature/AmazingFeature`)
3. ✨ 提交更改 (`git commit -m 'feat: Add AmazingFeature'`)
4. 📤 推送分支 (`git push origin feature/AmazingFeature`)
5. 🎉 提交 Pull Request

---

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源协议。

---

<div align="center">

**Made with ❤️ using Vue 3 + TypeScript**

</div>
