export interface Order {
  id: number
  orderNo: string
  userId: number
  totalAmount: number
  paymentAmount: number
  discountAmount: number
  freightAmount: number
  status: number
  paymentMethod: number
  paymentTime: string
  deliveryTime: string
  trackingNumber: string
  receiveTime: string
  receiverName: string
  receiverPhone: string
  receiverAddress: string
  items: OrderItem[]
  createdAt: string
}

export interface OrderItem {
  id: number
  productId: number
  productName: string
  productImage: string
  skuId: number
  skuName: string
  price: number
  quantity: number
  totalAmount: number
}

export interface OrderCreate {
  userId: number
  items: OrderItemInput[]
  couponId?: number
  addressId?: number
  receiverName: string
  receiverPhone: string
  receiverProvince: string
  receiverCity: string
  receiverDistrict: string
  receiverAddress: string
  receiverZipCode?: string
  remark?: string
  orderType?: number
  sourceType?: number
}

export interface OrderItemInput {
  productId: number
  skuId?: number
  quantity: number
}

export interface OrderListResult {
  list: Order[]
  total: number
  pageNum: number
  pageSize: number
}

export const OrderStatus = {
  PENDING_PAYMENT: 0,
  PAID: 1,
  PENDING_SHIPMENT: 2,
  SHIPPED: 3,
  DELIVERED: 4,
  CANCELLED: 5,
  REFUNDING: 6,
  REFUNDED: 7,
  CLOSED: 8,
} as const

export const OrderStatusText: Record<number, string> = {
  0: '待付款',
  1: '已付款',
  2: '待发货',
  3: '已发货',
  4: '已完成',
  5: '已取消',
  6: '退款中',
  7: '已退款',
  8: '已关闭',
}
