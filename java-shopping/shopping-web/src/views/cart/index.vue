<template>
  <div class="cart-page">
    <el-card>
      <template #header>
        <span>购物车</span>
      </template>
      <el-table :data="cartItems" v-if="cartItems.length > 0">
        <el-table-column label="商品" prop="productName" />
        <el-table-column label="单价" prop="price" />
        <el-table-column label="数量" prop="quantity" />
        <el-table-column label="小计">
          <template #default="{ row }">
            ¥{{ (row.price * row.quantity).toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="danger" link @click="removeItem(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="购物车为空" />
      <div class="cart-footer" v-if="cartItems.length > 0">
        <el-button type="primary" @click="checkout">去结算</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getCartList, removeCartItem } from '@/api/cart'
import type { CartItem } from '@/types/cart'

const router = useRouter()
const cartItems = ref<CartItem[]>([])

onMounted(() => {
  loadCart()
})

const loadCart = async () => {
  cartItems.value = await getCartList()
}

const removeItem = async (id: number) => {
  await removeCartItem(id)
  ElMessage.success('已删除')
  loadCart()
}

const checkout = () => {
  router.push('/checkout')
}
</script>

<style scoped>
.cart-page {
  padding: 20px;
}
.cart-footer {
  margin-top: 20px;
  text-align: right;
}
</style>
