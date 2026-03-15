package handler

import (
	"net/http"

	"github.com/zeromicro/go-zero/rest"

	"github.com/shopping/go-shopping/app/product/api/internal/svc"
)

func RegisterHandlers(server *rest.Server, serverCtx *svc.ServiceContext) {
	server.AddRoutes(
		[]rest.Route{
			{
				Method:  http.MethodGet,
				Path:    "/api/product/:id",
				Handler: GetProductHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/product/list",
				Handler: GetProductListHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/product/category/:id",
				Handler: GetProductByCategoryHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/product/search",
				Handler: SearchProductHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/product/hot",
				Handler: GetHotProductsHandler(serverCtx),
			},
			{
				Method:  http.MethodGet,
				Path:    "/api/product/new",
				Handler: GetNewProductsHandler(serverCtx),
			},
			{
				Method:  http.MethodPost,
				Path:    "/api/product/create",
				Handler: CreateProductHandler(serverCtx),
			},
			{
				Method:  http.MethodPut,
				Path:    "/api/product/:id",
				Handler: UpdateProductHandler(serverCtx),
			},
			{
				Method:  http.MethodDelete,
				Path:    "/api/product/:id",
				Handler: DeleteProductHandler(serverCtx),
			},
		},
	)
}
