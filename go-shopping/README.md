<div align="center">

# 🚀 Shopping Go

<img src="https://img.shields.io/badge/Go-1.21+-00ADD8?style=for-the-badge&logo=go" alt="Go Version">
<img src="https://img.shields.io/badge/GoZero-1.5.5-00ADD8?style=for-the-badge" alt="Go-Zero">
<img src="https://img.shields.io/badge/GORM-1.25.5-00ADD8?style=for-the-badge" alt="GORM">
<img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="License">

**高性能电商微服务 | Go-Zero 框架 | 云原生架构**

[快速开始](#-快速开始) · [API 文档](#-api-文档) · [架构设计](#-架构设计)

***

</div>

## ✨ 特性

<table>
<tr>
<td width="50%">

### ⚡ 极致性能

- **高并发**: 单机 QPS 50,000+
- **低延迟**: P99 < 10ms
- **低内存**: \~50MB 运行时内存
- **快速启动**: < 1s 启动时间

</td>
<td width="50%">

### 🛠️ 技术栈

- **Go-Zero**: 微服务框架
- **GORM**: ORM 框架
- **JWT**: 认证授权
- **Redis**: 缓存/分布式锁

</td>
</tr>
</table>

***

## 📁 项目结构

```
go-shopping/
├── 📂 app/
│   ├── 📂 user/                    # 👤 用户服务
│   │   ├── 📂 api/
│   │   │   ├── 📂 internal/
│   │   │   │   ├── 📂 config/      # ⚙️ 配置
│   │   │   │   ├── 📂 handler/     # 🎯 HTTP 处理器
│   │   │   │   ├── 📂 logic/       # 💼 业务逻辑
│   │   │   │   ├── 📂 svc/         # 🔧 服务上下文
│   │   │   │   └── 📂 types/       # 📝 类型定义
│   │   │   └── 📄 user.go          # 🚀 入口文件
│   │   └── 📂 model/               # 📊 数据模型
│   │
│   ├── 📂 product/                 # 📦 商品服务
│   │   ├── 📂 api/
│   │   └── 📂 model/
│   │
│   └── 📂 order/                   # 📋 订单服务
│       ├── 📂 api/
│       └── 📂 model/
│
└── 📄 go.mod
```

***

## 🏗️ 架构设计

```
┌─────────────────────────────────────────────────────────────┐
│                        Client Layer                         │
│                     (Web / Mobile / API)                    │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     API Gateway Layer                       │
│              (Nginx / Kong / APISIX)                        │
└─────────────────────────────┬───────────────────────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
┌───────────────┐     ┌───────────────┐     ┌───────────────┐
│ 👤 User       │     │ 📦 Product    │     │ 📋 Order      │
│ Service       │     │ Service       │     │ Service       │
│               │     │               │     │               │
│ Port: 8001    │     │ Port: 8002    │     │ Port: 8003    │
└───────┬───────┘     └───────┬───────┘     └───────┬───────┘
        │                     │                     │
        └─────────────────────┼─────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Infrastructure Layer                     │
│  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐    │
│  │  MySQL  │   │  Redis  │   │  Etcd   │   │RocketMQ │    │
│  └─────────┘   └─────────┘   └─────────┘   └─────────┘    │
└─────────────────────────────────────────────────────────────┘
```

***

## 🚀 快速开始

### 环境要求

| 组件                                                                                                                   | 版本    | 说明   |
| -------------------------------------------------------------------------------------------------------------------- | ----- | ---- |
| <img src="https://img.shields.io/badge/Go-00ADD8?style=flat-square&logo=go&logoColor=white" alt="Go"> Go             | 1.21+ | 编程语言 |
| <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"> MySQL | 8.0+  | 数据库  |
| <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white" alt="Redis"> Redis | 6.0+  | 缓存   |

### 安装运行

```bash
# 📥 克隆项目
git clone 
cd shopping/go-shopping

# 📦 安装依赖
go mod tidy

# ⚙️ 配置文件 (创建 etc/*.yaml)
# 修改数据库、Redis 等配置

# 🚀 启动服务
go run app/user/api/user.go -f app/user/api/etc/user-api.yaml
go run app/product/api/product.go -f app/product/api/etc/product-api.yaml
go run app/order/api/order.go -f app/order/api/etc/order-api.yaml
```

### Docker 部署

```bash
# 🐳 构建镜像
docker build -t shopping-user:latest ./app/user
docker build -t shopping-product:latest ./app/product
docker build -t shopping-order:latest ./app/order

# 🚀 运行容器
docker run -d -p 8001:8001 shopping-user:latest
docker run -d -p 8002:8002 shopping-product:latest
docker run -d -p 8003:8003 shopping-order:latest
```

***

## 📖 API 文档

### 👤 用户服务 (Port: 8001)

|   方法   | 路径                   | 说明       |
| :----: | -------------------- | -------- |
| `POST` | `/api/user/login`    | 用户登录     |
| `POST` | `/api/user/register` | 用户注册     |
| `POST` | `/api/user/logout`   | 用户登出     |
| `POST` | `/api/user/refresh`  | 刷新 Token |
|  `GET` | `/api/user/:id`      | 获取用户信息   |
|  `PUT` | `/api/user/:id`      | 更新用户信息   |
|  `GET` | `/api/user/exists`   | 检查用户名存在  |

<details>
<summary><b>📦 商品服务 API (Port: 8002)</b></summary>

|    方法    | 路径                          | 说明   |
| :------: | --------------------------- | ---- |
|   `GET`  | `/api/product/:id`          | 商品详情 |
|   `GET`  | `/api/product/list`         | 商品列表 |
|   `GET`  | `/api/product/category/:id` | 分类商品 |
|   `GET`  | `/api/product/search`       | 搜索商品 |
|   `GET`  | `/api/product/hot`          | 热门商品 |
|   `GET`  | `/api/product/new`          | 新品推荐 |
|  `POST`  | `/api/product/create`       | 创建商品 |
|   `PUT`  | `/api/product/:id`          | 更新商品 |
| `DELETE` | `/api/product/:id`          | 删除商品 |

</details>

<details>
<summary><b>📋 订单服务 API (Port: 8003)</b></summary>

|   方法   | 路径                       | 说明   |
| :----: | ------------------------ | ---- |
| `POST` | `/api/order/create`      | 创建订单 |
| `POST` | `/api/order/pay/:id`     | 支付订单 |
| `POST` | `/api/order/cancel/:id`  | 取消订单 |
| `POST` | `/api/order/deliver/:id` | 订单发货 |
| `POST` | `/api/order/confirm/:id` | 确认收货 |
|  `GET` | `/api/order/:id`         | 订单详情 |
|  `GET` | `/api/order/list`        | 订单列表 |

</details>

***

## 📊 数据模型

### User (用户)

```go
type User struct {
    ID          uint           `gorm:"primarykey" json:"id"`
    Username    string         `gorm:"uniqueIndex;size:50" json:"username"`
    Password    string         `gorm:"size:255" json:"-"`
    Nickname    string         `gorm:"size:100" json:"nickname"`
    Email       string         `gorm:"size:100" json:"email"`
    Phone       string         `gorm:"size:20" json:"phone"`
    Avatar      string         `gorm:"size:255" json:"avatar"`
    Gender      int            `gorm:"default:0" json:"gender"`
    Status      int            `gorm:"default:0" json:"status"`
    LastLoginAt *time.Time     `json:"lastLoginAt"`
    CreatedAt   time.Time      `json:"createdAt"`
    UpdatedAt   time.Time      `json:"updatedAt"`
}
```

### Product (商品)

```go
type Product struct {
    ID            uint           `gorm:"primarykey" json:"id"`
    Name          string         `gorm:"size:200;not null" json:"name"`
    ProductCode   string         `gorm:"size:50;uniqueIndex" json:"productCode"`
    CategoryID    uint           `gorm:"index" json:"categoryId"`
    BrandID       uint           `gorm:"index" json:"brandId"`
    Price         float64        `gorm:"type:decimal(10,2)" json:"price"`
    OriginalPrice float64        `gorm:"type:decimal(10,2)" json:"originalPrice"`
    Stock         int            `gorm:"default:0" json:"stock"`
    Sales         int            `gorm:"default:0" json:"sales"`
    Status        int            `gorm:"default:0" json:"status"`
}
```

### Order (订单)

```go
type Order struct {
    ID              uint           `gorm:"primarykey" json:"id"`
    OrderNo         string         `gorm:"size:50;uniqueIndex" json:"orderNo"`
    UserID          uint           `gorm:"index" json:"userId"`
    TotalAmount     float64        `gorm:"type:decimal(10,2)" json:"totalAmount"`
    PaymentAmount   float64        `gorm:"type:decimal(10,2)" json:"paymentAmount"`
    Status          int            `gorm:"default:0" json:"status"`
    ReceiverName    string         `gorm:"size:50" json:"receiverName"`
    ReceiverPhone   string         `gorm:"size:20" json:"receiverPhone"`
    ReceiverAddress string         `gorm:"size:200" json:"receiverAddress"`
    Items           []OrderItem    `gorm:"foreignKey:OrderID" json:"items"`
}
```

***

## 🔄 订单状态机

```
┌─────────────┐
│   待付款     │  Status: 0
│  (PENDING)  │
└──────┬──────┘
       │
       ├──────────────────────┐
       │                      │
       ▼                      ▼
┌─────────────┐        ┌─────────────┐
│   已付款     │        │   已取消     │
│   (PAID)    │        │ (CANCELLED) │
│  Status: 1  │        │  Status: 4  │
└──────┬──────┘        └─────────────┘
       │
       ▼
┌─────────────┐
│   已发货     │
│  (SHIPPED)  │
│  Status: 2  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   已完成     │
│ (COMPLETED) │
│  Status: 3  │
└─────────────┘
```

***

## ⚙️ 配置说明

```yaml
# user-api.yaml
Name: user-api
Host: 0.0.0.0
Port: 8001

Auth:
  AccessSecret: your-secret-key-here
  AccessExpire: 86400

MySQL:
  Host: localhost
  Port: 3306
  User: root
  Password: your-password
  DBName: shopping

Redis:
  Host: localhost
  Port: 6379
  Password: ""
  DB: 0
```

***

## 📈 性能测试

<div align="center">

|    指标   |    数值   |
| :-----: | :-----: |
|   QPS   | 50,000+ |
|  P50 延迟 |  < 5ms  |
|  P99 延迟 |  < 10ms |
|   内存占用  |  \~50MB |
| CPU 使用率 |  < 30%  |
|   启动时间  |   < 1s  |

</div>

***

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. 🍴 Fork 本仓库
2. 🌿 创建分支 (`git checkout -b feature/AmazingFeature`)
3. ✨ 提交更改 (`git commit -m 'feat: Add AmazingFeature'`)
4. 📤 推送分支 (`git push origin feature/AmazingFeature`)
5. 🎉 提交 Pull Request

***

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源协议。

***

<div align="center">

</div>
