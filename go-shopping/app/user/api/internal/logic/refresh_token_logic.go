package logic

import (
	"context"
	"time"

	"github.com/golang-jwt/jwt/v5"

	"github.com/shopping/go-shopping/app/user/api/internal/svc"
	"github.com/shopping/go-shopping/app/user/api/internal/types"
)

type RefreshTokenLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewRefreshTokenLogic(ctx context.Context, svcCtx *svc.ServiceContext) *RefreshTokenLogic {
	return &RefreshTokenLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *RefreshTokenLogic) RefreshToken(req *types.RefreshTokenRequest) (resp *types.LoginResponse, err error) {
	token, err := jwt.Parse(req.RefreshToken, func(token *jwt.Token) (interface{}, error) {
		return []byte(l.svcCtx.Config.Auth.AccessSecret), nil
	})
	if err != nil || !token.Valid {
		return nil, err
	}

	claims, ok := token.Claims.(jwt.MapClaims)
	if !ok {
		return nil, err
	}

	userID := uint(claims["userId"].(float64))
	username := claims["username"].(string)

	accessToken, err := l.generateToken(userID, username, l.svcCtx.Config.Auth.AccessExpire)
	if err != nil {
		return nil, err
	}

	newRefreshToken, err := l.generateToken(userID, username, l.svcCtx.Config.Auth.AccessExpire*7)
	if err != nil {
		return nil, err
	}

	return &types.LoginResponse{
		AccessToken:  accessToken,
		RefreshToken: newRefreshToken,
		TokenType:    "Bearer",
		ExpiresIn:    l.svcCtx.Config.Auth.AccessExpire,
	}, nil
}

func (l *RefreshTokenLogic) generateToken(userID uint, username string, expire int64) (string, error) {
	claims := jwt.MapClaims{
		"userId":   userID,
		"username": username,
		"exp":      time.Now().Add(time.Duration(expire) * time.Second).Unix(),
		"iat":      time.Now().Unix(),
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	return token.SignedString([]byte(l.svcCtx.Config.Auth.AccessSecret))
}
