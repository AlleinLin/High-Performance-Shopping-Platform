package handler

import (
	"net/http"

	"github.com/zeromicro/go-zero/rest"

	"github.com/shopping/go-shopping/app/order/api/internal/svc"
)

func RegisterHandlers(server *rest.Server, serverCtx *svc.ServiceContext) {
	server.AddRoutes(
		[]rest.Route{
			{
				Method:  http.MethodPost,
				Path:    "/api/order/create",
				Handler: CreateOrderHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/order/pay/:id",
				Handler: PayOrderHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/order/cancel/:id",
				Handler: CancelOrderHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/order/deliver/:id",
				Handler: DeliverOrderHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/order/confirm/:id",
				Handler: ConfirmReceiveHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/order/:id",
				Handler: GetOrderHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/order/list",
				Handler: GetOrderListHandler(serverCtx),
			},
		},
	)
}
