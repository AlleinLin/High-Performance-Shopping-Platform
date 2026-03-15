import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { UserInfo, LoginRequest, RegisterRequest } from '@/types/user'
import { login as loginApi, register as registerApi, logout as logoutApi, getUserInfo as getUserInfoApi } from '@/api/user'
import router from '@/router'

export const useUserStore = defineStore('user', () => {
  const token = ref<string>('')
  const refreshToken = ref<string>('')
  const userInfo = ref<UserInfo | null>(null)

  const isLoggedIn = computed(() => !!token.value)
  const username = computed(() => userInfo.value?.username || '')
  const avatar = computed(() => userInfo.value?.avatar || '')

  async function loginAction(data: LoginRequest) {
    const res = await loginApi(data)
    token.value = res.accessToken
    refreshToken.value = res.refreshToken
    userInfo.value = res.user
    return res
  }

  async function registerAction(data: RegisterRequest) {
    const res = await registerApi(data)
    token.value = res.accessToken
    refreshToken.value = res.refreshToken
    userInfo.value = res.user
    return res
  }

  async function getUserInfoAction() {
    if (!token.value) return null
    const res = await getUserInfoApi()
    userInfo.value = res
    return res
  }

  async function logoutAction() {
    try {
      await logoutApi()
    } finally {
      token.value = ''
      refreshToken.value = ''
      userInfo.value = null
      router.push('/login')
    }
  }

  function setToken(newToken: string) {
    token.value = newToken
  }

  return {
    token,
    refreshToken,
    userInfo,
    isLoggedIn,
    username,
    avatar,
    loginAction,
    registerAction,
    getUserInfoAction,
    logoutAction,
    setToken,
  }
}, {
  persist: {
    key: 'shopping-user',
    paths: ['token', 'refreshToken', 'userInfo'],
  },
})
