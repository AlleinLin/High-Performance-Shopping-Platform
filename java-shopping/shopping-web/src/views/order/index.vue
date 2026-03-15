<template>
  <div class="order-page">
    <el-card>
      <template #header>
        <span>我的订单</span>
      </template>
      <el-table :data="orders" v-if="orders.length > 0">
        <el-table-column label="订单号" prop="orderNo" />
        <el-table-column label="金额" prop="totalAmount" />
        <el-table-column label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createdAt" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="primary" link @click="goToDetail(row.id)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-else description="暂无订单" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getOrderList } from '@/api/order'
import type { Order } from '@/types/order'

const router = useRouter()
const orders = ref<Order[]>([])

onMounted(() => {
  loadOrders()
})

const loadOrders = async () => {
  const result = await getOrderList()
  orders.value = result.list
}

const goToDetail = (id: number) => {
  router.push(`/order/${id}`)
}

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
.order-page {
  padding: 20px;
}
</style>
