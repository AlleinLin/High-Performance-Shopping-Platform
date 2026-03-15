package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
)

type CancelOrderLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewCancelOrderLogic(ctx context.Context, svcCtx *svc.ServiceContext) *CancelOrderLogic {
	return &CancelOrderLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *CancelOrderLogic) CancelOrder(req *types.CancelOrderRequest) (resp *types.Response, err error) {
	order, err := l.svcCtx.OrderModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	order.Status = 4
	if err := l.svcCtx.OrderModel.Update(order); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Timestamp: time.Now().Unix(),
	}, nil
}
