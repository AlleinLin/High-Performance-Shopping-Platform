package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
)

type GetOrderListLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewGetOrderListLogic(ctx context.Context, svcCtx *svc.ServiceContext) *GetOrderListLogic {
	return &GetOrderListLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *GetOrderListLogic) GetOrderList(req *types.GetOrderListRequest) (resp *types.Response, err error) {
	orders, total, err := l.svcCtx.OrderModel.FindByUserID(req.UserID, req.Page, req.PageSize, req.Status)
	if err != nil {
		return nil, err
	}
	list := make([]types.OrderResponse, 0, len(orders))
	for _, order := range orders {
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
		list = append(list, result)
	}
	return &types.Response{
		Code:    200,
		Message: "success",
		Data: types.PageResponse{
			List:     list,
			Total:    total,
			Page:     req.Page,
			PageSize: req.PageSize,
		},
		Timestamp: time.Now().Unix(),
	}, nil
}
