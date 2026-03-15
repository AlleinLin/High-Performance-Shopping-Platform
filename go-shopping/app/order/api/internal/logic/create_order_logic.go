package logic

import (
	"context"
	"fmt"
	"time"

	"github.com/google/uuid"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
	"github.com/shopping/go-shopping/app/order/model"
)

type CreateOrderLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewCreateOrderLogic(ctx context.Context, svcCtx *svc.ServiceContext) *CreateOrderLogic {
	return &CreateOrderLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *CreateOrderLogic) CreateOrder(req *types.CreateOrderRequest) (resp *types.Response, err error) {
	orderNo := generateOrderNo()
	order := &model.Order{
		OrderNo:          orderNo,
		UserID:           req.UserID,
		ReceiverName:     req.ReceiverName,
		ReceiverPhone:    req.ReceiverPhone,
		ReceiverProvince: req.ReceiverProvince,
		ReceiverCity:     req.ReceiverCity,
		ReceiverDistrict: req.ReceiverDistrict,
		ReceiverAddress:  req.ReceiverAddress,
		ReceiverZipCode:  req.ReceiverZipCode,
		OrderType:        req.OrderType,
		SourceType:       req.SourceType,
		Remark:           req.Remark,
		Status:           0,
	}
	var totalAmount float64
	for _, item := range req.Items {
		orderItem := model.OrderItem{
			OrderNo:     orderNo,
			ProductID:   item.ProductID,
			SkuID:       item.SkuID,
			Quantity:    item.Quantity,
			TotalAmount: 0,
			RealAmount:  0,
		}
		order.Items = append(order.Items, orderItem)
		totalAmount += orderItem.TotalAmount
	}
	order.TotalAmount = totalAmount
	order.PaymentAmount = totalAmount
	if err := l.svcCtx.OrderModel.Insert(order); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Data:      order.ID,
		Timestamp: time.Now().Unix(),
	}, nil
}

func generateOrderNo() string {
	return fmt.Sprintf("%s%s", time.Now().Format("20060102150405"), uuid.New().String()[:8])
}
