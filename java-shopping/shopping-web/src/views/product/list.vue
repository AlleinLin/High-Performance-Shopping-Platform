<template>
  <div class="product-list">
    <el-row :gutter="20">
      <el-col v-for="product in products" :key="product.id" :span="6">
        <el-card @click="goToDetail(product.id)">
          <el-image :src="product.mainImage" fit="contain" />
          <div class="product-info">
            <h4>{{ product.name }}</h4>
            <p class="price">¥{{ product.price }}</p>
            <p class="sales">销量: {{ product.sales }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination
      v-model:current-page="page"
      :page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      @current-change="loadProducts"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProductList } from '@/api/product'
import type { Product } from '@/types/product'

const route = useRoute()
const router = useRouter()
const products = ref<Product[]>([])
const page = ref(1)
const pageSize = ref(12)
const total = ref(0)

onMounted(() => {
  loadProducts()
})

const loadProducts = async () => {
  const result = await getProductList({
    pageNum: page.value,
    pageSize: pageSize.value,
    categoryId: Number(route.params.id) || undefined,
  })
  products.value = result.list
  total.value = result.total
}

const goToDetail = (id: number) => {
  router.push(`/product/${id}`)
}
</script>

<style scoped>
.product-list {
  padding: 20px;
}
.product-info {
  padding: 10px 0;
}
.price {
  color: #f56c6c;
  font-weight: bold;
}
.sales {
  color: #999;
  font-size: 12px;
}
</style>
