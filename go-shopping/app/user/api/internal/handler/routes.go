package handler

import (
	"net/http"

	"github.com/zeromicro/go-zero/rest"

	"github.com/shopping/go-shopping/app/user/api/internal/svc"
)

func RegisterHandlers(server *rest.Server, serverCtx *svc.ServiceContext) {
	server.AddRoutes(
		[]rest.Route{
			{
				Method:  http.MethodPost,
				Path:    "/api/user/login",
				Handler: LoginHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/user/register",
				Handler: RegisterHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/user/logout",
				Handler: LogoutHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/user/refresh",
				Handler: RefreshTokenHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/user/:id",
				Handler: GetUserHandler(serverCtx),
			},
			{
				Method:  http.MethodPut,
				Path:    "/api/user/:id",
				Handler: UpdateUserHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/user/exists",
				Handler: ExistsByUsernameHandler(serverCtx),
			},
		},
	)
}
