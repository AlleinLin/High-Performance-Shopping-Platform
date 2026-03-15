<template>
  <div class="product-detail">
    <el-card v-if="product">
      <template #header>
        <span>{{ product.name }}</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-image :src="product.mainImage" fit="contain" />
        </el-col>
        <el-col :span="14">
          <h2>{{ product.name }}</h2>
          <p class="price">¥{{ product.price }}</p>
          <p v-if="product.originalPrice > product.price" class="original-price">
            原价: ¥{{ product.originalPrice }}
          </p>
          <p class="stock">库存: {{ product.stock }}</p>
          <p class="sales">销量: {{ product.sales }}</p>
          <el-divider />
          <p class="brief">{{ product.brief }}</p>
          <el-divider />
          <el-input-number v-model="quantity" :min="1" :max="product.stock" />
          <el-button type="primary" @click="addToCart">加入购物车</el-button>
          <el-button type="danger" @click="buyNow">立即购买</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getProductById } from '@/api/product'
import type { Product } from '@/types/product'

const route = useRoute()
const product = ref<Product | null>(null)
const quantity = ref(1)

onMounted(async () => {
  const id = Number(route.params.id)
  try {
    product.value = await getProductById(id)
  } catch {
    ElMessage.error('获取商品详情失败')
  }
})

const addToCart = () => {
  ElMessage.success('已加入购物车')
}

const buyNow = () => {
  ElMessage.info('功能开发中')
}
</script>

<style scoped>
.product-detail {
  padding: 20px;
}
.price {
  color: #f56c6c;
  font-size: 24px;
  font-weight: bold;
}
.original-price {
  color: #999;
  text-decoration: line-through;
}
</style>
