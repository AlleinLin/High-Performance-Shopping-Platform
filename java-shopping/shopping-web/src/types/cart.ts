export interface CartItem {
  id: number
  userId: number
  productId: number
  productName: string
  productImage: string
  skuId: number
  skuName: string
  price: number
  quantity: number
  checked: boolean
  stock: number
  productStatus: number
}

export interface CartItemAdd {
  productId: number
  skuId?: number
  quantity: number
}

export interface CartSummary {
  totalCount: number
  totalPrice: number
  selectedCount: number
  selectedPrice: number
}
