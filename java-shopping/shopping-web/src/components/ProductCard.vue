<template>
  <el-card class="product-card" shadow="hover" @click="goToDetail">
    <div class="product-image">
      <el-image :src="product.mainImage" fit="cover" lazy>
        <template #error>
          <div class="image-placeholder">
            <el-icon :size="48"><Picture /></el-icon>
          </div>
        </template>
      </el-image>
      <div v-if="product.sales > 100" class="hot-tag">热销</div>
    </div>
    <div class="product-info">
      <h3 class="product-name">{{ product.name }}</h3>
      <p class="product-brief">{{ product.brief }}</p>
      <div class="product-price">
        <span class="current-price">¥{{ product.price.toFixed(2) }}</span>
        <span v-if="product.originalPrice > product.price" class="original-price">
          ¥{{ product.originalPrice.toFixed(2) }}
        </span>
      </div>
      <div class="product-meta">
        <span>已售 {{ product.sales }}</span>
        <span>库存 {{ product.stock }}</span>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import type { Product } from '@/types/product'

const props = defineProps<{
  product: Product
}>()

const router = useRouter()

const goToDetail = () => {
  router.push(`/product/${props.product.id}`)
}
</script>

<style lang="scss" scoped>
.product-card {
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-5px);
  }
  
  :deep(.el-card__body) {
    padding: 0;
  }
  
  .product-image {
    position: relative;
    height: 200px;
    overflow: hidden;
    
    .el-image {
      width: 100%;
      height: 100%;
    }
    
    .image-placeholder {
      width: 100%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f5f7fa;
      color: #c0c4cc;
    }
    
    .hot-tag {
      position: absolute;
      top: 10px;
      left: 10px;
      background: linear-gradient(135deg, #ff6b6b, #ee5a5a);
      color: #fff;
      padding: 2px 8px;
      border-radius: 4px;
      font-size: 12px;
    }
  }
  
  .product-info {
    padding: 15px;
    
    .product-name {
      font-size: 16px;
      font-weight: 500;
      color: #303133;
      margin-bottom: 8px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    
    .product-brief {
      font-size: 12px;
      color: #909399;
      margin-bottom: 10px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    
    .product-price {
      margin-bottom: 10px;
      
      .current-price {
        font-size: 20px;
        font-weight: bold;
        color: #f56c6c;
      }
      
      .original-price {
        font-size: 14px;
        color: #909399;
        text-decoration: line-through;
        margin-left: 8px;
      }
    }
    
    .product-meta {
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      color: #909399;
    }
  }
}
</style>
