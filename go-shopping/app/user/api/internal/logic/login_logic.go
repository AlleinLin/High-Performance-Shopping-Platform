package logic

import (
	"context"
	"errors"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"golang.org/x/crypto/bcrypt"

	"github.com/shopping/go-shopping/app/user/api/internal/svc"
	"github.com/shopping/go-shopping/app/user/api/internal/types"
)

type LoginLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewLoginLogic(ctx context.Context, svcCtx *svc.ServiceContext) *LoginLogic {
	return &LoginLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *LoginLogic) Login(req *types.LoginRequest) (resp *types.LoginResponse, err error) {
	user, err := l.svcCtx.UserModel.FindOneByUsername(l.ctx, req.Username)
	if err != nil {
		return nil, errors.New("invalid username or password")
	}

	if user.Status != 0 {
		return nil, errors.New("account is disabled or locked")
	}

	err = bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(req.Password))
	if err != nil {
		return nil, errors.New("invalid username or password")
	}

	accessToken, err := l.generateToken(user.ID, user.Username, l.svcCtx.Config.Auth.AccessExpire)
	if err != nil {
		return nil, errors.New("failed to generate token")
	}

	refreshToken, err := l.generateToken(user.ID, user.Username, l.svcCtx.Config.Auth.AccessExpire*7)
	if err != nil {
		return nil, errors.New("failed to generate refresh token")
	}

	now := time.Now()
	user.LastLoginAt = &now
	user.LoginCount++
	l.svcCtx.UserModel.Update(l.ctx, user)

	return &types.LoginResponse{
		AccessToken:  accessToken,
		RefreshToken: refreshToken,
		TokenType:    "Bearer",
		ExpiresIn:    l.svcCtx.Config.Auth.AccessExpire,
		User: types.UserVO{
			ID:          user.ID,
			Username:    user.Username,
			Nickname:    user.Nickname,
			Email:       user.Email,
			Phone:       user.Phone,
			Avatar:      user.Avatar,
			Gender:      user.Gender,
			Birthday:    user.Birthday,
			Status:      user.Status,
			LastLoginAt: user.LastLoginAt.Format("2006-01-02 15:04:05"),
		},
	}, nil
}

func (l *LoginLogic) generateToken(userID uint, username string, expire int64) (string, error) {
	claims := jwt.MapClaims{
		"userId":   userID,
		"username": username,
		"exp":      time.Now().Add(time.Duration(expire) * time.Second).Unix(),
		"iat":      time.Now().Unix(),
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	return token.SignedString([]byte(l.svcCtx.Config.Auth.AccessSecret))
}
