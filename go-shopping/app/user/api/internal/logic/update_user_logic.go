package logic

import (
	"context"

	"github.com/shopping/go-shopping/app/user/api/internal/svc"
	"github.com/shopping/go-shopping/app/user/api/internal/types"
)

type UpdateUserLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUpdateUserLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UpdateUserLogic {
	return &UpdateUserLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UpdateUserLogic) UpdateUser(req *types.UpdateUserRequest) (resp *types.Response, err error) {
	user, err := l.svcCtx.UserModel.FindOne(l.ctx, req.ID)
	if err != nil {
		return nil, err
	}

	if req.Nickname != "" {
		user.Nickname = req.Nickname
	}
	if req.Email != "" {
		user.Email = req.Email
	}
	if req.Phone != "" {
		user.Phone = req.Phone
	}
	if req.Avatar != "" {
		user.Avatar = req.Avatar
	}
	user.Gender = req.Gender
	if req.Birthday != "" {
		user.Birthday = req.Birthday
	}

	if err := l.svcCtx.UserModel.Update(l.ctx, user); err != nil {
		return nil, err
	}

	return &types.Response{
		Code:    200,
		Message: "success",
	}, nil
}
