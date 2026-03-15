package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
	"github.com/shopping/go-shopping/app/order/api/internal/types"
)

type ConfirmReceiveLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewConfirmReceiveLogic(ctx context.Context, svcCtx *svc.ServiceContext) *ConfirmReceiveLogic {
	return &ConfirmReceiveLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ConfirmReceiveLogic) ConfirmReceive(req *types.ConfirmReceiveRequest) (resp *types.Response, err error) {
	order, err := l.svcCtx.OrderModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	order.Status = 3
	now := time.Now()
	order.ReceiveTime = &now
	if err := l.svcCtx.OrderModel.Update(order); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Timestamp: time.Now().Unix(),
	}, nil
}
