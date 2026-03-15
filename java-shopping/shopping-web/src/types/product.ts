export interface Product {
  id: number
  name: string
  productCode: string
  categoryId: number
  categoryName: string
  brandId: number
  brandName: string
  mainImage: string
  subImages: string[]
  price: number
  originalPrice: number
  stock: number
  sales: number
  status: number
  brief: string
  description: string
}

export interface ProductListParams {
  pageNum?: number
  pageSize?: number
  categoryId?: number
  brandId?: number
  keyword?: string
  status?: number
}

export interface ProductListResult {
  list: Product[]
  total: number
  pageNum: number
  pageSize: number
  pages: number
  hasNext: boolean
  hasPrevious: boolean
}

export interface Category {
  id: number
  parentId: number
  name: string
  level: number
  icon: string
  image: string
  sort: number
  showStatus: boolean
  children?: Category[]
}

export interface Brand {
  id: number
  name: string
  firstLetter: string
  logo: string
  description: string
  sort: number
  showStatus: boolean
}
