<template>
  <div class="checkout-page">
    <el-card>
      <template #header>
        <span>订单结算</span>
      </template>
      <el-form :model="form" label-width="100px">
        <el-form-item label="收货人">
          <el-input v-model="form.receiverName" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.receiverPhone" />
        </el-form-item>
        <el-form-item label="省份">
          <el-input v-model="form.receiverProvince" />
        </el-form-item>
        <el-form-item label="城市">
          <el-input v-model="form.receiverCity" />
        </el-form-item>
        <el-form-item label="区/县">
          <el-input v-model="form.receiverDistrict" />
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="form.receiverAddress" type="textarea" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitOrder">提交订单</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createOrder } from '@/api/order'
import type { OrderCreate } from '@/types/order'

const router = useRouter()
const form = ref<Omit<OrderCreate, 'userId' | 'items'>>({
  receiverName: '',
  receiverPhone: '',
  receiverProvince: '',
  receiverCity: '',
  receiverDistrict: '',
  receiverAddress: '',
  remark: '',
})

const submitOrder = async () => {
  if (!form.value.receiverName || !form.value.receiverPhone || !form.value.receiverAddress) {
    ElMessage.warning('请填写完整的收货信息')
    return
  }
  try {
    const order = await createOrder({
      ...form.value,
      userId: 0,
      items: [],
    })
    ElMessage.success('订单创建成功')
    router.push(`/order/${order.id}`)
  } catch {
    ElMessage.error('订单创建失败')
  }
}
</script>

<style scoped>
.checkout-page {
  padding: 20px;
}
</style>
