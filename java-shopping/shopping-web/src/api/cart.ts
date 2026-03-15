import { get, post, del } from '@/utils/request'
import type { CartItem, CartItemAdd } from '@/types/cart'

export function getCartList(): Promise<CartItem[]> {
  return get('/cart/list')
}

export function getCartCount(): Promise<number> {
  return get('/cart/count')
}

export function addToCart(data: CartItemAdd): Promise<CartItem> {
  return post('/cart/add', data)
}

export function updateCartItem(id: number, quantity: number): Promise<CartItem> {
  return post(`/cart/item/${id}`, { quantity })
}

export function removeCartItem(id: number): Promise<void> {
  return del(`/cart/item/${id}`)
}

export function clearCart(): Promise<void> {
  return del('/cart/clear')
}

export function checkCartItem(id: number, checked: boolean): Promise<void> {
  return post(`/cart/item/${id}/check`, { checked })
}

export function checkAllItems(checked: boolean): Promise<void> {
  return post('/cart/check-all', { checked })
}
