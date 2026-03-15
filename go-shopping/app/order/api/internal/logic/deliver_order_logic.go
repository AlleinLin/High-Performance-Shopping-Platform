package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
)

type DeliverOrderLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewDeliverOrderLogic(ctx context.Context, svcCtx *svc.ServiceContext) *DeliverOrderLogic {
	return &DeliverOrderLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *DeliverOrderLogic) DeliverOrder(req *types.DeliverOrderRequest) (resp *types.Response, err error) {
	order, err := l.svcCtx.OrderModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	order.Status = 2
	order.DeliveryCompany = req.DeliveryCompany
	order.TrackingNumber = req.TrackingNumber
	now := time.Now()
	order.DeliveryTime = &now
	if err := l.svcCtx.OrderModel.Update(order); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Timestamp: time.Now().Unix(),
	}, nil
}
