<template>
  <div class="home-page">
    <section class="hero-section">
      <el-carousel height="400px" :interval="5000">
        <el-carousel-item v-for="item in banners" :key="item.id">
          <div class="banner-item" :style="{ backgroundImage: `url(${item.image})` }">
            <div class="banner-content">
              <h1>{{ item.title }}</h1>
              <p>{{ item.subtitle }}</p>
              <el-button type="primary" size="large" @click="router.push(item.link)">
                立即选购
              </el-button>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </section>
    
    <section class="category-section">
      <div class="section-header">
        <h2>商品分类</h2>
      </div>
      <div class="category-grid">
        <div
          v-for="category in categories"
          :key="category.id"
          class="category-card"
          @click="router.push(`/category/${category.id}`)"
        >
          <el-icon :size="48" :color="category.color">
            <component :is="category.icon" />
          </el-icon>
          <span>{{ category.name }}</span>
        </div>
      </div>
    </section>
    
    <section class="product-section">
      <div class="section-header">
        <h2>热销商品</h2>
        <el-button text @click="router.push('/category/0')">
          查看更多 <el-icon><ArrowRight /></el-icon>
        </el-button>
      </div>
      <div class="product-grid">
        <product-card
          v-for="product in hotProducts"
          :key="product.id"
          :product="product"
        />
      </div>
    </section>
    
    <section class="product-section">
      <div class="section-header">
        <h2>新品上市</h2>
        <el-button text @click="router.push('/category/0')">
          查看更多 <el-icon><ArrowRight /></el-icon>
        </el-button>
      </div>
      <div class="product-grid">
        <product-card
          v-for="product in newProducts"
          :key="product.id"
          :product="product"
        />
      </div>
    </section>
    
    <section class="feature-section">
      <div class="feature-grid">
        <div class="feature-item">
          <el-icon :size="40" color="#409EFF"><Van /></el-icon>
          <h3>免费配送</h3>
          <p>订单满99元免运费</p>
        </div>
        <div class="feature-item">
          <el-icon :size="40" color="#67C23A"><CircleCheck /></el-icon>
          <h3>品质保证</h3>
          <p>正品保障，假一赔十</p>
        </div>
        <div class="feature-item">
          <el-icon :size="40" color="#E6A23C"><RefreshRight /></el-icon>
          <h3>7天无理由</h3>
          <p>不满意可退换货</p>
        </div>
        <div class="feature-item">
          <el-icon :size="40" color="#F56C6C"><Service /></el-icon>
          <h3>专业客服</h3>
          <p>7x24小时在线服务</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import ProductCard from '@/components/ProductCard.vue'
import type { Product } from '@/types/product'
import { 
  Iphone,
  Monitor,
  House,
  ShoppingBag,
  Bicycle,
  Reading,
  Food,
  MagicStick,
  Van,
  CircleCheck,
  RefreshRight,
  Service,
  ArrowRight
} from '@element-plus/icons-vue'

const router = useRouter()

const banners = ref([
  { id: 1, title: '新品首发', subtitle: 'iPhone 15 Pro Max 现已开售', image: 'https://via.placeholder.com/1400x400?text=iPhone+15+Pro', link: '/product/1' },
  { id: 2, title: '限时特惠', subtitle: '全场数码产品低至8折', image: 'https://via.placeholder.com/1400x400?text=Sale', link: '/category/1' },
  { id: 3, title: '品质生活', subtitle: '精选好物，品质之选', image: 'https://via.placeholder.com/1400x400?text=Quality+Life', link: '/category/3' },
])

const categories = ref([
  { id: 1, name: '手机数码', icon: Iphone, color: '#409EFF' },
  { id: 2, name: '电脑办公', icon: Monitor, color: '#67C23A' },
  { id: 3, name: '家用电器', icon: House, color: '#E6A23C' },
  { id: 4, name: '服饰鞋包', icon: ShoppingBag, color: '#F56C6C' },
  { id: 5, name: '运动户外', icon: Bicycle, color: '#909399' },
  { id: 6, name: '图书音像', icon: Reading, color: '#9B59B6' },
  { id: 7, name: '食品生鲜', icon: Food, color: '#1ABC9C' },
  { id: 8, name: '美妆个护', icon: MagicStick, color: '#E91E63' },
])

const hotProducts = ref<Product[]>([])
const newProducts = ref<Product[]>([])

const fetchData = async () => {
  try {
    // Mock data for demonstration
    hotProducts.value = [
      { id: 1, name: 'iPhone 15 Pro Max', mainImage: 'https://via.placeholder.com/300', price: 9999, originalPrice: 10999, sales: 500, stock: 100, brief: 'Latest iPhone', productCode: 'P001', categoryId: 1, brandId: 1, status: 2, description: '' } as Product,
      { id: 2, name: 'MacBook Pro 14"', mainImage: 'https://via.placeholder.com/300', price: 14999, originalPrice: 16999, sales: 200, stock: 50, brief: 'MacBook Pro M3', productCode: 'P002', categoryId: 2, brandId: 1, status: 2, description: '' } as Product,
    ]
    newProducts.value = [
      { id: 3, name: 'iPad Pro 12.9"', mainImage: 'https://via.placeholder.com/300', price: 8999, originalPrice: 9999, sales: 150, stock: 60, brief: 'iPad Pro M2', productCode: 'P003', categoryId: 3, brandId: 1, status: 2, description: '' } as Product,
      { id: 4, name: 'AirPods Pro 2', mainImage: 'https://via.placeholder.com/300', price: 1899, originalPrice: 1999, sales: 800, stock: 200, brief: 'AirPods Pro', productCode: 'P004', categoryId: 1, brandId: 1, status: 2, description: '' } as Product,
    ]
  } catch (error) {
    console.error('Failed to fetch products:', error)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="scss" scoped>
.home-page {
  max-width: 1400px;
  margin: 0 auto;
}

.hero-section {
  margin-bottom: 40px;
  
  .banner-item {
    height: 100%;
    background-size: cover;
    background-position: center;
    display: flex;
    align-items: center;
    justify-content: center;
    
    .banner-content {
      text-align: center;
      color: #fff;
      text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
      
      h1 {
        font-size: 48px;
        margin-bottom: 10px;
      }
      
      p {
        font-size: 20px;
        margin-bottom: 20px;
      }
    }
  }
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  
  h2 {
    font-size: 24px;
    font-weight: 600;
    color: #303133;
  }
}

.category-section {
  margin-bottom: 40px;
  
  .category-grid {
    display: grid;
    grid-template-columns: repeat(8, 1fr);
    gap: 15px;
    
    @media (max-width: 1200px) {
      grid-template-columns: repeat(4, 1fr);
    }
    
    @media (max-width: 768px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .category-card {
    background: #fff;
    border-radius: 8px;
    padding: 20px;
    text-align: center;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }
    
    span {
      display: block;
      margin-top: 10px;
      font-size: 14px;
      color: #606266;
    }
  }
}

.product-section {
  margin-bottom: 40px;
  
  .product-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    
    @media (max-width: 1200px) {
      grid-template-columns: repeat(3, 1fr);
    }
    
    @media (max-width: 768px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
}

.feature-section {
  background: #fff;
  border-radius: 8px;
  padding: 40px 20px;
  margin-bottom: 40px;
  
  .feature-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 30px;
    
    @media (max-width: 768px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .feature-item {
    text-align: center;
    
    h3 {
      margin: 15px 0 10px;
      font-size: 18px;
      color: #303133;
    }
    
    p {
      font-size: 14px;
      color: #909399;
    }
  }
}
</style>
