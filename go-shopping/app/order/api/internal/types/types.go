package types

import "time"

type CreateOrderRequest struct {
	UserID           uint             `json:"userId"`
	Items            []OrderItemInput `json:"items"`
	CouponID         uint             `json:"couponId,optional"`
	ReceiverName     string           `json:"receiverName"`
	ReceiverPhone    string           `json:"receiverPhone"`
	ReceiverProvince string           `json:"receiverProvince"`
	ReceiverCity     string           `json:"receiverCity"`
	ReceiverDistrict string           `json:"receiverDistrict"`
	ReceiverAddress  string           `json:"receiverAddress"`
	ReceiverZipCode  string           `json:"receiverZipCode,optional"`
	Remark           string           `json:"remark,optional"`
	OrderType        int              `json:"orderType,optional"`
	SourceType       int              `json:"sourceType,optional"`
}

type OrderItemInput struct {
	ProductID uint `json:"productId"`
	SkuID     uint `json:"skuId,optional"`
	Quantity  int  `json:"quantity"`
}

type OrderResponse struct {
	ID              uint          `json:"id"`
	OrderNo         string        `json:"orderNo"`
	UserID          uint          `json:"userId"`
	TotalAmount     float64       `json:"totalAmount"`
	PaymentAmount   float64       `json:"paymentAmount"`
	DiscountAmount  float64       `json:"discountAmount"`
	FreightAmount   float64       `json:"freightAmount"`
	Status          int           `json:"status"`
	PaymentMethod   int           `json:"paymentMethod"`
	PaymentTime     string        `json:"paymentTime"`
	DeliveryTime    string        `json:"deliveryTime"`
	DeliveryCompany string        `json:"deliveryCompany"`
	TrackingNumber  string        `json:"trackingNumber"`
	ReceiveTime     string        `json:"receiveTime"`
	ReceiverName    string        `json:"receiverName"`
	ReceiverPhone   string        `json:"receiverPhone"`
	ReceiverAddress string        `json:"receiverAddress"`
	Items           []OrderItemVO `json:"items"`
	CreatedAt       string        `json:"createdAt"`
}

type OrderItemVO struct {
	ID           uint    `json:"id"`
	ProductID    uint    `json:"productId"`
	ProductName  string  `json:"productName"`
	ProductImage string  `json:"productImage"`
	SkuID        uint    `json:"skuId"`
	SkuName      string  `json:"skuName"`
	Price        float64 `json:"price"`
	Quantity     int     `json:"quantity"`
	TotalAmount  float64 `json:"totalAmount"`
}

type PayOrderRequest struct {
	ID            uint `path:"id"`
	PaymentMethod int  `json:"paymentMethod"`
}

type CancelOrderRequest struct {
	ID uint `path:"id"`
}

type DeliverOrderRequest struct {
	ID              uint   `path:"id"`
	DeliveryCompany string `json:"deliveryCompany"`
	TrackingNumber  string `json:"trackingNumber"`
}

type ConfirmReceiveRequest struct {
	ID uint `path:"id"`
}

type GetOrderRequest struct {
	ID uint `path:"id"`
}

type GetOrderListRequest struct {
	UserID   uint `form:"userId"`
	Page     int  `form:"page,default=1"`
	PageSize int  `form:"pageSize,default=10"`
	Status   int  `form:"status,default=-1"`
}

type Response struct {
	Code      int         `json:"code"`
	Message   string      `json:"message"`
	Data      interface{} `json:"data,omitempty"`
	Timestamp int64       `json:"timestamp"`
}

type PageResponse struct {
	List     interface{} `json:"list"`
	Total    int64       `json:"total"`
	Page     int         `json:"page"`
	PageSize int         `json:"pageSize"`
}

func Success(data interface{}) Response {
	return Response{
		Code:      200,
		Message:   "success",
		Data:      data,
		Timestamp: time.Now().Unix(),
	}
}

func Error(code int, message string) Response {
	return Response{
		Code:      code,
		Message:   message,
		Timestamp: time.Now().Unix(),
	}
}
