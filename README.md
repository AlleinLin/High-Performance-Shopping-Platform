<div align="center">

# 🛒 Shopping Cloud

<img src="https://img.shields.io/badge/Go-1.21+-00ADD8?style=for-the-badge&logo=go" alt="Go Version">
<img src="https://img.shields.io/badge/Java-21+-ED8B00?style=for-the-badge&logo=java" alt="Java Version">
<img src="https://img.shields.io/badge/Vue-3.4+-4FC08D?style=for-the-badge&logo=vue.js" alt="Vue Version">
<img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="License">

**电商微服务平台 | 双语言实现 | 云原生架构**

[🚀 快速开始](#-快速开始) · [📖 文档](#-文档) · [🤝 贡献](#-贡献指南) · [💬 讨论](https://github.com/discussions)

***

</div>

## ✨ 项目亮点

<table>
<tr>
<td width="50%">

### 🔥 双语言实现

提供 **Go** 和 **Java** 两种微服务实现，满足不同场景需求：

- Go 版本：高性能、低延迟、资源占用少
- Java 版本：生态完善、企业级特性丰富

</td>
<td width="50%">

### ☁️ 云原生架构

基于主流云原生技术栈构建：

- 容器化部署 (Docker/Kubernetes)
- 服务网格支持
- 可观测性完善

</td>
</tr>
<tr>
<td width="50%">

### 🔐 企业级特性

内置多种企业级能力：

- 分布式事务 (Seata)
- 分布式锁 (Redisson)
- 接口幂等性
- 限流熔断

</td>
<td width="50%">

### 🎨 现代化前端

基于 Vue 3 + TypeScript 构建：

- 组件化开发
- 响应式设计
- TypeScript 类型安全

</td>
</tr>
</table>

***

## 📊 版本对比

| 特性       | <img src="https://img.shields.io/badge/Go-00ADD8?style=flat-square&logo=go&logoColor=white" alt="Go"> Go 版本 | <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white" alt="Java"> Java 版本 |
| :------- | :---------------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------------------: |
| **框架**   |                                                   Go-Zero                                                   |                                                 Spring Cloud Alibaba                                                |
| **性能**   |                                                    ⭐⭐⭐⭐⭐                                                    |                                                         ⭐⭐⭐                                                         |
| **内存占用** |                                                    ⭐⭐⭐⭐⭐                                                    |                                                          ⭐⭐                                                         |
| **开发效率** |                                                     ⭐⭐⭐                                                     |                                                        ⭐⭐⭐⭐⭐                                                        |
| **生态完善** |                                                     ⭐⭐⭐                                                     |                                                        ⭐⭐⭐⭐⭐                                                        |
| **学习曲线** |                                                     ⭐⭐⭐                                                     |                                                          ⭐⭐                                                         |
| **适用场景** |                                                   高性能/低延迟                                                   |                                                       企业级/复杂业务                                                      |

***

## 🏗️ 系统架构

```
                                    ┌─────────────────────────────────────────────────────────────┐
                                    │                      🌐 Client Layer                        │
                                    │  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐    │
                                    │  │  Web    │   │ Mobile  │   │  H5     │   │ MiniApp │    │
                                    │  └────┬────┘   └────┬────┘   └────┬────┘   └────┬────┘    │
                                    └───────┼─────────────┼─────────────┼─────────────┼─────────┘
                                            │             │             │             │
                                            └─────────────┴──────┬──────┴─────────────┘
                                                                 │
                                    ┌────────────────────────────▼────────────────────────────┐
                                    │                    🚪 API Gateway                        │
                                    │              (认证 / 限流 / 路由 / 熔断)                  │
                                    └────────────────────────────┬────────────────────────────┘
                                                                 │
                    ┌────────────────────────────────────────────┼────────────────────────────────────────────┐
                    │                                            │                                            │
          ┌─────────▼─────────┐                        ┌─────────▼─────────┐                        ┌─────────▼─────────┐
          │                   │                        │                   │                        │                   │
          │  👤 User Service  │                        │ 📦 Product Service│                        │ 📋 Order Service  │
          │                   │                        │                   │                        │                   │
          │  • 用户注册登录    │                        │  • 商品管理        │                        │  • 订单创建        │
          │  • JWT认证授权     │                        │  • 库存管理        │                        │  • 订单支付        │
          │  • 用户信息管理    │                        │  • 分类品牌        │                        │  • 订单状态        │
          │                   │                        │  • 搜索推荐        │                        │  • 物流跟踪        │
          └─────────┬─────────┘                        └─────────┬─────────┘                        └─────────┬─────────┘
                    │                                            │                                            │
                    └────────────────────────────────────────────┼────────────────────────────────────────────┘
                                                                 │
                                    ┌────────────────────────────▼────────────────────────────┐
                                    │                  🔧 Infrastructure                        │
                                    │  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   │
                                    │  │  MySQL  │   │  Redis  │   │  Nacos  │   │RocketMQ │   │
                                    │  └─────────┘   └─────────┘   └─────────┘   └─────────┘   │
                                    └─────────────────────────────────────────────────────────┘
```

***

## 📁 项目结构

```
shopping/
├── 📂 go-shopping/                 # Go 微服务版本
│   ├── 📂 app/
│   │   ├── 📂 user/               # 👤 用户服务
│   │   ├── 📂 product/            # 📦 商品服务
│   │   └── 📂 order/              # 📋 订单服务
│   ├── 📄 go.mod
│   └── 📄 README.md
│
├── 📂 java-shopping/               # Java 微服务版本
│   ├── 📂 shopping-api/           # 📡 API 定义
│   ├── 📂 shopping-common/        # 🔧 公共模块
│   ├── 📂 shopping-gateway/       # 🚪 网关服务
│   ├── 📂 shopping-user/          # 👤 用户服务
│   ├── 📂 shopping-product/       # 📦 商品服务
│   ├── 📂 shopping-order/         # 📋 订单服务
│   ├── 📂 shopping-web/           # 🎨 前端应用
│   ├── 📄 pom.xml
│   └── 📄 README.md
│
└── 📄 README.md                    # 📖 本文件
```

***

## 🛠️ 技术栈

### 后端服务

<table>
<tr>
<th>Go 版本</th>
<th>Java 版本</th>
</tr>
<tr>
<td>

| 技术      | 版本     |
| ------- | ------ |
| Go      | 1.21+  |
| Go-Zero | 1.5.5  |
| GORM    | 1.25.5 |
| JWT     | 5.2.0  |
| Redis   | -      |

</td>
<td>

| 技术                   | 版本       |
| -------------------- | -------- |
| Java                 | 21+      |
| Spring Boot          | 3.2.2    |
| Spring Cloud         | 2023.0.0 |
| Spring Cloud Alibaba | 2023.0.0 |
| MyBatis-Plus         | 3.5.5    |
| Seata                | 2.0.0    |

</td>
</tr>
</table>

### 前端应用

| 技术           | 版本     | 说明     |
| ------------ | ------ | ------ |
| Vue          | 3.4.15 | 渐进式框架  |
| TypeScript   | 5.3.3  | 类型支持   |
| Vite         | 5.0.11 | 构建工具   |
| Element Plus | 2.5.3  | UI 组件库 |
| Pinia        | 2.1.7  | 状态管理   |

***

## 🚀 快速开始

### 环境要求

| 组件                                                                                                                           | 版本要求  |
| ---------------------------------------------------------------------------------------------------------------------------- | ----- |
| <img src="https://img.shields.io/badge/Go-00ADD8?style=flat-square&logo=go&logoColor=white" alt="Go"> Go                     | 1.21+ |
| <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white" alt="Java"> Java             | 21+   |
| <img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=node.js&logoColor=white" alt="Node.js"> Node.js | 18+   |
| <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"> MySQL         | 8.0+  |
| <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white" alt="Redis"> Redis         | 6.0+  |

### 启动 Go 版本

```bash
# 📥 克隆项目
git clone 
cd shopping/go-shopping

# 📦 安装依赖
go mod tidy

# 🚀 启动服务
go run app/user/api/user.go -f app/user/api/etc/user-api.yaml
go run app/product/api/product.go -f app/product/api/etc/product-api.yaml
go run app/order/api/order.go -f app/order/api/etc/order-api.yaml
```

### 启动 Java 版本

```bash
# 📥 进入项目目录
cd shopping/java-shopping

# 🔨 构建项目
mvn clean install -DskipTests

# 🚀 启动服务
java -jar shopping-gateway/target/shopping-gateway.jar
java -jar shopping-user/target/shopping-user.jar
java -jar shopping-product/target/shopping-product.jar
java -jar shopping-order/target/shopping-order.jar
```

### 启动前端

```bash
# 📥 进入前端目录
cd shopping/java-shopping/shopping-web

# 📦 安装依赖
npm install

# 🚀 启动开发服务器
npm run dev
```

<div align="center">

### 🌐 服务端口

|  服务  |  Go  | Java |
| :--: | :--: | :--: |
|  网关  |   -  | 8080 |
| 用户服务 | 8001 | 8081 |
| 商品服务 | 8002 | 8082 |
| 订单服务 | 8003 | 8083 |
|  前端  | 3000 | 3000 |

</div>

***

## 📖 文档

| 文档                                             | 说明           |
| ---------------------------------------------- | ------------ |
| [Go 版本文档](./go-shopping/README.md)             | Go 微服务详细文档   |
| [Java 版本文档](./java-shopping/README.md)         | Java 微服务详细文档 |
| [前端文档](./java-shopping/shopping-web/README.md) | Vue 前端详细文档   |

***

## 🎯 核心功能

<details>
<summary><b>👤 用户服务</b></summary>

| 功能       | 说明             |
| -------- | -------------- |
| 用户注册     | 支持用户名/邮箱/手机号注册 |
| 用户登录     | JWT Token 认证   |
| Token 刷新 | 无感刷新 Token     |
| 用户信息     | 查看/修改个人信息      |

</details>

<details>
<summary><b>📦 商品服务</b></summary>

| 功能   | 说明         |
| ---- | ---------- |
| 商品管理 | 商品 CRUD 操作 |
| 分类管理 | 多级分类支持     |
| 品牌管理 | 品牌信息管理     |
| 库存管理 | 库存扣减/回滚    |
| 商品搜索 | 关键词搜索      |
| 热门推荐 | 热销/新品推荐    |

</details>

<details>
<summary><b>📋 订单服务</b></summary>

| 功能   | 说明      |
| ---- | ------- |
| 创建订单 | 下单购买商品  |
| 订单支付 | 支付订单金额  |
| 订单取消 | 取消未支付订单 |
| 订单发货 | 商家发货操作  |
| 确认收货 | 用户确认收货  |
| 订单列表 | 查看订单记录  |

</details>

***

## 🔐 企业级特性

<table>
<tr>
<td width="33%" align="center">

### 🔒 分布式锁

```java
@DistributedLock(key = "order:#id")
public void processOrder(Long id) {
    // 业务逻辑
}
```

</td>
<td width="33%" align="center">

### 🔄 接口幂等

```java
@Idempotent(key = "create:#userId")
public Order createOrder(Long userId) {
    // 创建订单
}
```

</td>
<td width="33%" align="center">

### ⚡ 限流熔断

```java
@RateLimit(limit = 100, period = 60)
public Product getProduct(Long id) {
    // 获取商品
}
```

</td>
</tr>
</table>

***

## 📈 性能指标

<div align="center">

|     指标     |  Go 版本  | Java 版本 |
| :--------: | :-----: | :-----: |
|  QPS (单机)  | 50,000+ | 15,000+ |
| 响应时间 (P99) |  < 10ms |  < 50ms |
|    内存占用    |  \~50MB | \~300MB |
|    启动时间    |   < 1s  |  \~10s  |

</div>

***

## 🤝 贡献指南

我们欢迎所有形式的贡献！

<table>
<tr>
<td width="50%">

### 🎯 如何贡献

1. 🍴 Fork 本仓库
2. 🌿 创建分支 (`git checkout -b feature/AmazingFeature`)
3. ✨ 提交更改 (`git commit -m 'feat: Add AmazingFeature'`)
4. 📤 推送分支 (`git push origin feature/AmazingFeature`)
5. 🎉 提交 PR

</td>
<td width="50%">

### 📝 提交规范

| 类型         | 说明     |
| ---------- | ------ |
| `feat`     | 新功能    |
| `fix`      | 修复 Bug |
| `docs`     | 文档更新   |
| `style`    | 代码格式   |
| `refactor` | 重构     |
| `test`     | 测试     |
| `chore`    | 构建/工具  |

</td>
</tr>
</table>

***

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源协议。

***

<div align="center">

### ⭐ Star History

如果这个项目对你有帮助，请给一个 ⭐ Star 支持一下！

</div>
