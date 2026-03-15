<template>
  <div class="search-page">
    <el-input
      v-model="keyword"
      placeholder="搜索商品"
      @keyup.enter="search"
    >
      <template #append>
        <el-button @click="search">搜索</el-button>
      </template>
    </el-input>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col v-for="product in products" :key="product.id" :span="6">
        <el-card @click="goToDetail(product.id)">
          <el-image :src="product.mainImage" fit="contain" />
          <div class="product-info">
            <h4>{{ product.name }}</h4>
            <p class="price">¥{{ product.price }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { searchProducts } from '@/api/product'
import type { Product } from '@/types/product'

const route = useRoute()
const router = useRouter()
const keyword = ref('')
const products = ref<Product[]>([])

onMounted(() => {
  keyword.value = route.query.keyword as string || ''
  if (keyword.value) {
    doSearch()
  }
})

const search = () => {
  router.push({ path: '/search', query: { keyword: keyword.value } })
  doSearch()
}

const doSearch = async () => {
  const result = await searchProducts(keyword.value)
  products.value = result.list
}

const goToDetail = (id: number) => {
  router.push(`/product/${id}`)
}
</script>

<style scoped>
.search-page {
  padding: 20px;
}
.product-info {
  padding: 10px 0;
}
.price {
  color: #f56c6c;
  font-weight: bold;
}
</style>
