package types

import "time"

type LoginRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type LoginResponse struct {
	AccessToken  string `json:"accessToken"`
	RefreshToken string `json:"refreshToken"`
	TokenType    string `json:"tokenType"`
	ExpiresIn    int64  `json:"expiresIn"`
	User         UserVO `json:"user"`
}

type RegisterRequest struct {
	Username        string `json:"username"`
	Password        string `json:"password"`
	ConfirmPassword string `json:"confirmPassword"`
	Email           string `json:"email,optional"`
	Phone           string `json:"phone,optional"`
}

type RefreshTokenRequest struct {
	RefreshToken string `json:"refreshToken"`
}

type GetUserRequest struct {
	ID uint `path:"id"`
}

type UpdateUserRequest struct {
	ID       uint   `path:"id"`
	Nickname string `json:"nickname,optional"`
	Email    string `json:"email,optional"`
	Phone    string `json:"phone,optional"`
	Avatar   string `json:"avatar,optional"`
	Gender   int    `json:"gender,optional"`
	Birthday string `json:"birthday,optional"`
}

type ExistsByUsernameRequest struct {
	Username string `form:"username"`
}

type UserVO struct {
	ID          uint   `json:"id"`
	Username    string `json:"username"`
	Nickname    string `json:"nickname"`
	Email       string `json:"email"`
	Phone       string `json:"phone"`
	Avatar      string `json:"avatar"`
	Gender      int    `json:"gender"`
	Birthday    string `json:"birthday"`
	Status      int    `json:"status"`
	LastLoginAt string `json:"lastLoginAt"`
}

type Response struct {
	Code      int         `json:"code"`
	Message   string      `json:"message"`
	Data      interface{} `json:"data,omitempty"`
	Timestamp int64       `json:"timestamp"`
}

func Success(data interface{}) Response {
	return Response{
		Code:      200,
		Message:   "success",
		Data:      data,
		Timestamp: time.Now().Unix(),
	}
}

func Error(code int, message string) Response {
	return Response{
		Code:      code,
		Message:   message,
		Timestamp: time.Now().Unix(),
	}
}
