import { post, get } from '@/utils/request'
import type { UserInfo, LoginRequest, RegisterRequest, LoginResponse } from '@/types/user'

export function login(data: LoginRequest): Promise<LoginResponse> {
  return post('/user/login', data)
}

export function register(data: RegisterRequest): Promise<LoginResponse> {
  return post('/user/register', data)
}

export function logout(): Promise<void> {
  return post('/user/logout')
}

export function getUserInfo(): Promise<UserInfo> {
  return get('/user/info')
}

export function updateUser(id: number, data: Partial<UserInfo>): Promise<UserInfo> {
  return post(`/user/${id}`, data)
}

export function existsByUsername(username: string): Promise<boolean> {
  return get('/user/exists', { params: { username } })
}

export function existsByEmail(email: string): Promise<boolean> {
  return get('/user/email/exists', { params: { email } })
}
