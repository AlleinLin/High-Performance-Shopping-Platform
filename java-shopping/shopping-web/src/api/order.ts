import { get, post } from '@/utils/request'
import type { Order, OrderCreate, OrderListResult } from '@/types/order'

export function createOrder(data: OrderCreate): Promise<Order> {
  return post('/order/create', data)
}

export function getOrderById(id: number): Promise<Order> {
  return get(`/order/${id}`)
}

export function getOrderByNo(orderNo: string): Promise<Order> {
  return get(`/order/no/${orderNo}`)
}

export function getOrderList(page: number = 1, pageSize: number = 10, status?: number): Promise<OrderListResult> {
  return get('/order/list', { params: { page, pageSize, status } })
}

export function payOrder(id: number, paymentMethod: number): Promise<void> {
  return post(`/order/${id}/pay`, null, { params: { paymentMethod } })
}

export function cancelOrder(id: number): Promise<void> {
  return post(`/order/${id}/cancel`)
}

export function confirmReceive(id: number): Promise<void> {
  return post(`/order/${id}/confirm`)
}

export function deleteOrder(id: number): Promise<void> {
  return post(`/order/${id}/delete`)
}
