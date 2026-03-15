<template>
  <el-container class="main-layout">
    <el-header class="header">
      <div class="header-content">
        <div class="logo" @click="router.push('/')">
          <el-icon :size="32" color="#409EFF"><ShoppingCart /></el-icon>
          <span class="logo-text">Shopping Cloud</span>
        </div>
        
        <div class="search-box">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索商品..."
            size="large"
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button @click="handleSearch">
                <el-icon><Search /></el-icon>
              </el-button>
            </template>
          </el-input>
        </div>
        
        <div class="header-actions">
          <el-badge :value="cartCount" :hidden="cartCount === 0" class="cart-badge">
            <el-button @click="router.push('/cart')" circle>
              <el-icon :size="20"><ShoppingCart /></el-icon>
            </el-button>
          </el-badge>
          
          <template v-if="userStore.isLoggedIn">
            <el-dropdown @command="handleCommand">
              <span class="user-dropdown">
                <el-avatar :size="36" :src="userStore.avatar">
                  {{ userStore.username?.charAt(0)?.toUpperCase() }}
                </el-avatar>
                <span class="username">{{ userStore.username }}</span>
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="user">
                    <el-icon><User /></el-icon>个人中心
                  </el-dropdown-item>
                  <el-dropdown-item command="order">
                    <el-icon><List /></el-icon>我的订单
                  </el-dropdown-item>
                  <el-dropdown-item divided command="logout">
                    <el-icon><SwitchButton /></el-icon>退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button type="primary" @click="router.push('/login')">登录</el-button>
            <el-button @click="router.push('/register')">注册</el-button>
          </template>
        </div>
      </div>
    </el-header>
    
    <el-main class="main">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </el-main>
    
    <el-footer class="footer">
      <div class="footer-content">
        <p>&copy; 2024 Shopping Cloud. All rights reserved.</p>
        <p>Enterprise E-commerce Platform powered by Spring Cloud & Vue 3</p>
      </div>
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { 
  ShoppingCart, 
  Search, 
  ArrowDown, 
  User, 
  List, 
  SwitchButton 
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const searchKeyword = ref('')
const cartCount = ref(0)

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({ name: 'Search', query: { keyword: searchKeyword.value } })
  }
}

const handleCommand = (command: string) => {
  switch (command) {
    case 'user':
      router.push('/user')
      break
    case 'order':
      router.push('/order')
      break
    case 'logout':
      userStore.logoutAction()
      break
  }
}

onMounted(() => {
  cartCount.value = 0
})
</script>

<style lang="scss" scoped>
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
  height: 70px;
  display: flex;
  align-items: center;
  
  .header-content {
    width: 100%;
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  
  .logo {
    display: flex;
    align-items: center;
    gap: 10px;
    cursor: pointer;
    
    .logo-text {
      font-size: 24px;
      font-weight: bold;
      background: linear-gradient(135deg, #409EFF, #67C23A);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
  }
  
  .search-box {
    flex: 1;
    max-width: 500px;
    margin: 0 40px;
  }
  
  .header-actions {
    display: flex;
    align-items: center;
    gap: 15px;
    
    .cart-badge {
      margin-right: 10px;
    }
    
    .user-dropdown {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      
      .username {
        font-size: 14px;
        color: #333;
      }
    }
  }
}

.main {
  flex: 1;
  background: #f5f7fa;
  padding: 20px;
}

.footer {
  background: #2c3e50;
  color: #fff;
  height: auto;
  padding: 30px 20px;
  
  .footer-content {
    text-align: center;
    
    p {
      margin: 5px 0;
      font-size: 14px;
      color: rgba(255, 255, 255, 0.8);
    }
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
