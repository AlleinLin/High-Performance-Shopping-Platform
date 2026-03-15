<template>
  <div class="order-detail">
    <el-card v-if="order">
      <template #header>
        <span>订单详情 - {{ order.orderNo }}</span>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ order.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(order.status)">{{ getStatusText(order.status) }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="总金额">¥{{ order.totalAmount }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ order.createdAt }}</el-descriptions-item>
        <el-descriptions-item label="收货人">{{ order.receiverName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ order.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="收货地址" :span="2">{{ order.receiverAddress }}</el-descriptions-item>
      </el-descriptions>
      <el-divider />
      <h4>商品列表</h4>
      <el-table :data="order.items">
        <el-table-column label="商品" prop="productName" />
        <el-table-column label="单价" prop="price" />
        <el-table-column label="数量" prop="quantity" />
        <el-table-column label="小计">
          <template #default="{ row }">
            ¥{{ (row.price * row.quantity).toFixed(2) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getOrderById } from '@/api/order'
import type { Order } from '@/types/order'

const route = useRoute()
const order = ref<Order | null>(null)

onMounted(async () => {
  const id = Number(route.params.id)
  order.value = await getOrderById(id)
})

const getStatusType = (status: number): 'primary' | 'success' | 'warning' | 'info' | 'danger' => {
  const types: Record<number, 'primary' | 'success' | 'warning' | 'info' | 'danger'> = {
    0: 'warning',
    1: 'primary',
    2: 'info',
    3: 'success',
    4: 'danger',
  }
  return types[status] ?? 'info'
}

const getStatusText = (status: number) => {
  const texts: Record<number, string> = {
    0: '待付款',
    1: '已付款',
    2: '已发货',
    3: '已完成',
    4: '已取消',
  }
  return texts[status] || '未知'
}
</script>

<style scoped>
.order-detail {
  padding: 20px;
}
</style>
