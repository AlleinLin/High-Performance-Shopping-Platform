<div align="center">

# ☕ Shopping Java

<img src="https://img.shields.io/badge/Java-21+-ED8B00?style=for-the-badge&logo=java" alt="Java Version">
<img src="https://img.shields.io/badge/Spring_Boot-3.2.2-6DB33F?style=for-the-badge&logo=springboot" alt="Spring Boot">
<img src="https://img.shields.io/badge/Spring_Cloud-2023.0.0-6DB33F?style=for-the-badge&logo=spring" alt="Spring Cloud">
<img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge" alt="License">

**电商微服务 | Spring Cloud Alibaba | 云原生架构**

[快速开始](#-快速开始) · [API 文档](#-api-文档) · [架构设计](#-架构设计)

***

</div>

## ✨ 特性

<table>
<tr>
<td width="50%">

### 🏢 企业级特性

- **分布式事务**: Seata AT 模式
- **分布式锁**: Redisson 实现
- **接口幂等**: Token 机制
- **限流熔断**: Sentinel 支持
- **链路追踪**: SkyWalking 集成

</td>
<td width="50%">

### 🛠️ 技术栈

- **Spring Boot 3.2**: 基础框架
- **Spring Cloud 2023**: 微服务套件
- **Spring Cloud Alibaba**: 阿里巴巴组件
- **MyBatis-Plus 3.5**: ORM 框架
- **Seata 2.0**: 分布式事务

</td>
</tr>
</table>

***

## 📁 项目结构

```
java-shopping/
├── 📂 shopping-api/                # 📡 API 模块
│   ├── 📂 dto/                     # 数据传输对象
│   │   ├── 📄 UserDTO.java
│   │   ├── 📄 ProductDTO.java
│   │   └── 📄 OrderDTO.java
│   ├── 📂 feign/                   # Feign 客户端
│   └── 📂 fallback/                # 降级处理
│
├── 📂 shopping-common/             # 🔧 公共模块
│   ├── 📂 annotation/              # 自定义注解
│   │   ├── 📄 @DistributedLock
│   │   ├── 📄 @Idempotent
│   │   └── 📄 @RateLimit
│   ├── 📂 aop/                     # 切面实现
│   ├── 📂 config/                  # 配置类
│   ├── 📂 constant/                # 常量定义
│   ├── 📂 enums/                   # 枚举类
│   ├── 📂 exception/               # 异常处理
│   ├── 📂 result/                  # 统一响应
│   └── 📂 util/                    # 工具类
│
├── 📂 shopping-gateway/            # 🚪 网关服务 (8080)
│   ├── 📂 config/
│   ├── 📂 filter/
│   └── 📄 GatewayServiceApplication.java
│
├── 📂 shopping-user/               # 👤 用户服务 (8081)
│   ├── 📂 controller/
│   ├── 📂 service/
│   ├── 📂 mapper/
│   ├── 📂 entity/
│   └── 📄 UserServiceApplication.java
│
├── 📂 shopping-product/            # 📦 商品服务 (8082)
│   ├── 📂 controller/
│   ├── 📂 service/
│   ├── 📂 mapper/
│   ├── 📂 entity/
│   └── 📄 ProductServiceApplication.java
│
├── 📂 shopping-order/              # 📋 订单服务 (8083)
│   ├── 📂 controller/
│   ├── 📂 service/
│   ├── 📂 mapper/
│   ├── 📂 entity/
│   └── 📄 OrderServiceApplication.java
│
├── 📂 shopping-web/                # 🎨 前端应用
│
└── 📄 pom.xml
```

***

## 🏗️ 系统架构

```
                                    ┌─────────────────────────────────────────────────────────────┐
                                    │                      🌐 Client Layer                        │
                                    │  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐    │
                                    │  │   Web   │   │ Mobile  │   │   H5    │   │ MiniApp │    │
                                    │  └────┬────┘   └────┬────┘   └────┬────┘   └────┬────┘    │
                                    └───────┼─────────────┼─────────────┼─────────────┼─────────┘
                                            │             │             │             │
                                            └─────────────┴──────┬──────┴─────────────┘
                                                                 │
                                    ┌────────────────────────────▼────────────────────────────┐
                                    │                    🚪 Spring Gateway                     │
                                    │          认证 / 限流 / 路由 / 熔断 / 日志 / 链路追踪        │
                                    │                        Port: 8080                        │
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
          │  • 用户信息管理    │◄──────────────────────►│  • 分类品牌        │◄──────────────────────►│  • 订单状态        │
          │                   │      OpenFeign         │  • 搜索推荐        │      OpenFeign         │  • 物流跟踪        │
          │   Port: 8081      │                        │   Port: 8082      │                        │   Port: 8083      │
          └─────────┬─────────┘                        └─────────┬─────────┘                        └─────────┬─────────┘
                    │                                            │                                            │
                    └────────────────────────────────────────────┼────────────────────────────────────────────┘
                                                                 │
                                    ┌────────────────────────────▼────────────────────────────┐
                                    │                  🔧 Infrastructure Layer                  │
                                    │  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   │
                                    │  │  MySQL  │   │  Redis  │   │  Nacos  │   │ Seata   │   │
                                    │  │   8.0   │   │   6.0   │   │   2.x   │   │   2.0   │   │
                                    │  └─────────┘   └─────────┘   └─────────┘   └─────────┘   │
                                    │  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   │
                                    │  │RocketMQ │   │  ES     │   │Sentinel │   │SkyWalk  │   │
                                    │  │   5.x   │   │   8.x   │   │  1.8    │   │   9.x   │   │
                                    │  └─────────┘   └─────────┘   └─────────┘   └─────────┘   │
                                    └─────────────────────────────────────────────────────────┘
```

***

## 🛠️ 技术栈

### 核心框架

| 技术                                                                                                                                                                  | 版本       | 说明     |
| :------------------------------------------------------------------------------------------------------------------------------------------------------------------ | :------- | :----- |
| <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white" alt="Java"> Java                                                    | 21       | 编程语言   |
| <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white" alt="Spring Boot"> Spring Boot                         | 3.2.2    | 基础框架   |
| <img src="https://img.shields.io/badge/Spring_Cloud-6DB33F?style=flat-square&logo=spring&logoColor=white" alt="Spring Cloud"> Spring Cloud                          | 2023.0.0 | 微服务框架  |
| <img src="https://img.shields.io/badge/Spring_Cloud_Alibaba-FF6A00?style=flat-square&logo=alibaba&logoColor=white" alt="Spring Cloud Alibaba"> Spring Cloud Alibaba | 2023.0.0 | 阿里巴巴套件 |

### 数据存储

| 技术                                                                                                                   | 版本     | 说明      |
| :------------------------------------------------------------------------------------------------------------------- | :----- | :------ |
| <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"> MySQL | 8.0.33 | 关系型数据库  |
| <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white" alt="Redis"> Redis | 6.0+   | 缓存/分布式锁 |
| <img src="https://img.shields.io/badge/MyBatis_Plus-BA3733?style=flat-square" alt="MyBatis-Plus"> MyBatis-Plus       | 3.5.5  | ORM 框架  |

### 微服务组件

| 技术         | 版本    | 说明        |
| :--------- | :---- | :-------- |
| Nacos      | 2.x   | 注册中心/配置中心 |
| Seata      | 2.0.0 | 分布式事务     |
| Sentinel   | 1.8.x | 限流熔断      |
| RocketMQ   | 5.x   | 消息队列      |
| SkyWalking | 9.x   | 链路追踪      |

***

## 🚀 快速开始

### 环境要求

| 组件                                                                                                                         | 版本要求 |
| -------------------------------------------------------------------------------------------------------------------------- | ---- |
| <img src="https://img.shields.io/badge/JDK-ED8B00?style=flat-square&logo=java&logoColor=white" alt="JDK"> JDK              | 21+  |
| <img src="https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white" alt="Maven"> Maven | 3.8+ |
| <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" alt="MySQL"> MySQL       | 8.0+ |
| <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white" alt="Redis"> Redis       | 6.0+ |
| <img src="https://img.shields.io/badge/Nacos-00B8D9?style=flat-square" alt="Nacos"> Nacos                                  | 2.x  |

### 安装运行

```bash
# 📥 克隆项目
git clone 
cd shopping/java-shopping

# 🔨 构建项目
mvn clean install -DskipTests

# 🚀 启动服务 (按顺序)
# 1. 启动网关
java -jar shopping-gateway/target/shopping-gateway.jar

# 2. 启动用户服务
java -jar shopping-user/target/shopping-user.jar

# 3. 启动商品服务
java -jar shopping-product/target/shopping-product.jar

# 4. 启动订单服务
java -jar shopping-order/target/shopping-order.jar
```

### Docker Compose 部署

```bash
# 🐳 一键启动所有服务
docker-compose up -d

# 📋 查看服务状态
docker-compose ps

# 📜 查看日志
docker-compose logs -f
```

***

## 📖 API 文档

启动服务后访问 Knife4j 文档：

| 服务        | 地址                               |
| --------- | -------------------------------- |
| 🚪 网关聚合文档 | <http://localhost:8080/doc.html> |
| 👤 用户服务   | <http://localhost:8081/doc.html> |
| 📦 商品服务   | <http://localhost:8082/doc.html> |
| 📋 订单服务   | <http://localhost:8083/doc.html> |

### 👤 用户服务 API

|   方法   | 路径                   | 说明     |
| :----: | -------------------- | ------ |
| `POST` | `/api/user/login`    | 用户登录   |
| `POST` | `/api/user/register` | 用户注册   |
| `POST` | `/api/user/logout`   | 用户登出   |
|  `GET` | `/api/user/{id}`     | 获取用户信息 |
|  `PUT` | `/api/user/{id}`     | 更新用户信息 |

<details>
<summary><b>📦 商品服务 API</b></summary>

|    方法    | 路径                    | 说明   |
| :------: | --------------------- | ---- |
|   `GET`  | `/api/product/{id}`   | 商品详情 |
|   `GET`  | `/api/product/list`   | 商品列表 |
|   `GET`  | `/api/product/search` | 搜索商品 |
|  `POST`  | `/api/product`        | 创建商品 |
|   `PUT`  | `/api/product/{id}`   | 更新商品 |
| `DELETE` | `/api/product/{id}`   | 删除商品 |

</details>

<details>
<summary><b>📋 订单服务 API</b></summary>

|   方法   | 路径                       | 说明   |
| :----: | ------------------------ | ---- |
| `POST` | `/api/order/create`      | 创建订单 |
| `POST` | `/api/order/pay/{id}`    | 支付订单 |
| `POST` | `/api/order/cancel/{id}` | 取消订单 |
|  `GET` | `/api/order/{id}`        | 订单详情 |
|  `GET` | `/api/order/list`        | 订单列表 |

</details>

***

## 🔐 特性

### 🔒 分布式锁

```java
@DistributedLock(key = "'order:' + #orderId", waitTime = 3000)
public void processOrder(Long orderId) {
    // 业务逻辑，自动获取分布式锁
}
```

### 🔄 接口幂等

```java
@Idempotent(key = "'create_order:' + #request.userId", expireTime = 5)
public Order createOrder(OrderCreateRequest request) {
    // 5秒内重复请求会被拦截
}
```

### ⚡ 限流熔断

```java
@RateLimit(key = "'product_detail'", limit = 100, period = 60)
public Product getProductDetail(Long id) {
    // 每分钟最多100次请求
}
```

***

## 📊 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1705382400000
}
```

### 响应码说明

| Code | 说明    |
| :--: | ----- |
|  200 | 成功    |
|  400 | 参数错误  |
|  401 | 未授权   |
|  403 | 禁止访问  |
|  404 | 资源不存在 |
|  500 | 服务器错误 |

***

## 📈 性能指标

<div align="center">

|    指标    |    数值   |
| :------: | :-----: |
| QPS (单机) | 15,000+ |
|  P99 延迟  |  < 50ms |
|   内存占用   | \~300MB |
|   启动时间   |  \~10s  |
|  CPU 使用率 |  < 50%  |

</div>

***

## 🗄️ 数据库设计

<details>
<summary><b>👤 用户表 (user)</b></summary>

```sql
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `nickname` varchar(100) COMMENT '昵称',
  `email` varchar(100) COMMENT '邮箱',
  `phone` varchar(20) COMMENT '手机号',
  `avatar` varchar(255) COMMENT '头像',
  `gender` int DEFAULT 0 COMMENT '性别',
  `status` int DEFAULT 0 COMMENT '状态',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

</details>

<details>
<summary><b>📦 商品表 (product)</b></summary>

```sql
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `product_code` varchar(50) NOT NULL COMMENT '商品编码',
  `category_id` bigint COMMENT '分类ID',
  `brand_id` bigint COMMENT '品牌ID',
  `main_image` varchar(500) COMMENT '主图',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `original_price` decimal(10,2) COMMENT '原价',
  `stock` int DEFAULT 0 COMMENT '库存',
  `sales` int DEFAULT 0 COMMENT '销量',
  `status` int DEFAULT 0 COMMENT '状态',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_code` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';
```

</details>

<details>
<summary><b>📋 订单表 (order)</b></summary>

```sql
CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `payment_amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `status` int DEFAULT 0 COMMENT '订单状态',
  `receiver_name` varchar(50) NOT NULL COMMENT '收货人',
  `receiver_phone` varchar(20) NOT NULL COMMENT '联系电话',
  `receiver_address` varchar(200) NOT NULL COMMENT '收货地址',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
```

</details>

***

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. 🍴 Fork 本仓库
2. 🌿 创建分支 (`git checkout -b feature/AmazingFeature`)
3. ✨ 提交更改 (`git commit -m 'feat: Add AmazingFeature'`)
4. 📤 推送分支 (`git push origin feature/AmazingFeature`)
5. 🎉 提交 Pull Request

### 提交规范

| 类型         | 说明     |
| ---------- | ------ |
| `feat`     | 新功能    |
| `fix`      | 修复 Bug |
| `docs`     | 文档更新   |
| `style`    | 代码格式   |
| `refactor` | 重构     |
| `test`     | 测试     |
| `chore`    | 构建/工具  |

***

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源协议。

***

<div align="center">

</div>
