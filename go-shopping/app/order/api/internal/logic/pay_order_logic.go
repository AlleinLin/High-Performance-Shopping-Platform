package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
)

type PayOrderLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewPayOrderLogic(ctx context.Context, svcCtx *svc.ServiceContext) *PayOrderLogic {
	return &PayOrderLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *PayOrderLogic) PayOrder(req *types.PayOrderRequest) (resp *types.Response, err error) {
	order, err := l.svcCtx.OrderModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	order.Status = 1
	order.PaymentMethod = req.PaymentMethod
	now := time.Now()
	order.PaymentTime = &now
	if err := l.svcCtx.OrderModel.Update(order); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Timestamp: time.Now().Unix(),
	}, nil
}
