package logic

import (
	"context"

	"github.com/shopping/go-shopping/app/user/api/internal/svc"
	"github.com/shopping/go-shopping/app/user/api/internal/types"
)

type ExistsByUsernameLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewExistsByUsernameLogic(ctx context.Context, svcCtx *svc.ServiceContext) *ExistsByUsernameLogic {
	return &ExistsByUsernameLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *ExistsByUsernameLogic) ExistsByUsername(req *types.ExistsByUsernameRequest) (resp *types.Response, err error) {
	exists, err := l.svcCtx.UserModel.ExistsByUsername(l.ctx, req.Username)
	if err != nil {
		return nil, err
	}

	return &types.Response{
		Code:    200,
		Message: "success",
		Data:    exists,
	}, nil
}
