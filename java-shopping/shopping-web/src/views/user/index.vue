<template>
  <div class="user-center">
    <el-card>
      <template #header>
        <span>个人中心</span>
      </template>
      <el-tabs>
        <el-tab-pane label="基本信息">
          <el-form :model="userInfo" label-width="100px">
            <el-form-item label="用户名">
              <el-input v-model="userInfo.username" disabled />
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="userInfo.nickname" />
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="userInfo.email" />
            </el-form-item>
            <el-form-item label="手机">
              <el-input v-model="userInfo.phone" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveUserInfo">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="我的订单">
          <router-link to="/order">查看全部订单</router-link>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getUserInfo, updateUser } from '@/api/user'
import type { UserInfo } from '@/types/user'

const userInfo = ref<Partial<UserInfo>>({})

onMounted(async () => {
  try {
    userInfo.value = await getUserInfo()
  } catch {
    ElMessage.error('获取用户信息失败')
  }
})

const saveUserInfo = async () => {
  try {
    await updateUser(userInfo.value.id!, userInfo.value)
    ElMessage.success('保存成功')
  } catch {
    ElMessage.error('保存失败')
  }
}
</script>

<style scoped>
.user-center {
  padding: 20px;
}
</style>
