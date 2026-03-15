import { get } from '@/utils/request'
import type { Product, ProductListParams, ProductListResult } from '@/types/product'

export function getProductList(params: ProductListParams): Promise<ProductListResult> {
  return get('/product/list', { params })
}

export function getProductById(id: number): Promise<Product> {
  return get(`/product/${id}`)
}

export function searchProducts(keyword: string, page: number = 1, pageSize: number = 10): Promise<ProductListResult> {
  return get('/product/search', { params: { keyword, page, pageSize } })
}

export function getHotProducts(limit: number = 10): Promise<Product[]> {
  return get('/product/hot', { params: { limit } })
}

export function getNewProducts(limit: number = 10): Promise<Product[]> {
  return get('/product/new', { params: { limit } })
}

export function getRecommendProducts(userId?: number, limit: number = 10): Promise<Product[]> {
  return get('/product/recommend', { params: { userId, limit } })
}
