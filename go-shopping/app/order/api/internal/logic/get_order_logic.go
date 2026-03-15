package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
)

type GetOrderLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewGetOrderLogic(ctx context.Context, svcCtx *svc.ServiceContext) *GetOrderLogic {
	return &GetOrderLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *GetOrderLogic) GetOrder(req *types.GetOrderRequest) (resp *types.Response, err error) {
	order, err := l.svcCtx.OrderModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	items := make([]types.OrderItemVO, 0, len(order.Items))
	for _, item := range order.Items {
		items = append(items, types.OrderItemVO{
			ID:           item.ID,
			ProductID:    item.ProductID,
			ProductName:  item.ProductName,
			ProductImage: item.ProductImage,
			SkuID:        item.SkuID,
			SkuName:      item.SkuName,
			Price:        item.Price,
			Quantity:     item.Quantity,
			TotalAmount:  item.TotalAmount,
		})
	}
	result := types.OrderResponse{
		ID:              order.ID,
		OrderNo:         order.OrderNo,
		UserID:          order.UserID,
		TotalAmount:     order.TotalAmount,
		PaymentAmount:   order.PaymentAmount,
		DiscountAmount:  order.DiscountAmount,
		FreightAmount:   order.FreightAmount,
		Status:          order.Status,
		PaymentMethod:   order.PaymentMethod,
		DeliveryCompany: order.DeliveryCompany,
		TrackingNumber:  order.TrackingNumber,
		ReceiverName:    order.ReceiverName,
		ReceiverPhone:   order.ReceiverPhone,
		ReceiverAddress: order.ReceiverAddress,
		Items:           items,
		CreatedAt:       order.CreatedAt.Format("2006-01-02 15:04:05"),
	}
	if order.PaymentTime != nil {
		result.PaymentTime = order.PaymentTime.Format("2006-01-02 15:04:05")
	}
	if order.DeliveryTime != nil {
		result.DeliveryTime = order.DeliveryTime.Format("2006-01-02 15:04:05")
	}
	if order.ReceiveTime != nil {
		result.ReceiveTime = order.ReceiveTime.Format("2006-01-02 15:04:05")
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Data:      result,
		Timestamp: time.Now().Unix(),
	}, nil
}
