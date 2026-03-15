<template>
  <div class="register-page">
    <el-card class="register-card">
      <template #header>
        <span>用户注册</span>
      </template>
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" show-password />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
          <el-button @click="goToLogin">已有账号？去登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { register } from '@/api/user'

const router = useRouter()
const formRef = ref<FormInstance>()
const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: '',
})

const rules = reactive<FormRules>({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (_rule, value, callback) => {
        if (value !== form.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur',
    },
  ],
})

const handleRegister = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return
  
  try {
    await register(form)
    ElMessage.success('注册成功')
    router.push('/login')
  } catch {
    ElMessage.error('注册失败')
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.register-page {
  display: flex;
  justify-content: center;
  padding-top: 100px;
}
.register-card {
  width: 500px;
}
</style>
