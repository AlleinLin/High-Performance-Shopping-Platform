package logic

import (
	"context"
	"errors"

	"golang.org/x/crypto/bcrypt"

	"github.com/shopping/go-shopping/app/user/api/internal/svc"
	"github.com/shopping/go-shopping/app/user/api/internal/types"
	"github.com/shopping/go-shopping/app/user/model"
)

type RegisterLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewRegisterLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RegisterLogic {
	return &RegisterLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *RegisterLogic) Register(req *types.RegisterRequest) (resp *types.LoginResponse, err error) {
	if req.Password != req.ConfirmPassword {
		return nil, errors.New("passwords do not match")
	}

	exists, _ := l.svcCtx.UserModel.ExistsByUsername(l.ctx, req.Username)
	if exists {
		return nil, errors.New("username already exists")
	}

	if req.Email != "" {
		exists, _ = l.svcCtx.UserModel.ExistsByEmail(l.ctx, req.Email)
		if exists {
			return nil, errors.New("email already registered")
		}
	}

	if req.Phone != "" {
		exists, _ = l.svcCtx.UserModel.ExistsByPhone(l.ctx, req.Phone)
		if exists {
			return nil, errors.New("phone already registered")
		}
	}

	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(req.Password), bcrypt.DefaultCost)
	if err != nil {
		return nil, errors.New("failed to hash password")
	}

	user := &model.User{
		Username: req.Username,
		Password: string(hashedPassword),
		Nickname: req.Username,
		Email:    req.Email,
		Phone:    req.Phone,
		Status:   0,
		Gender:   0,
	}

	err = l.svcCtx.UserModel.Insert(l.ctx, user)
	if err != nil {
		return nil, errors.New("failed to create user")
	}

	loginLogic := NewLoginLogic(l.ctx, l.svcCtx)
	return loginLogic.Login(&types.LoginRequest{
		Username: req.Username,
		Password: req.Password,
	})
}
