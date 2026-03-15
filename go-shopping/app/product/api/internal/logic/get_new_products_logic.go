package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/product/api/internal/svc"
	"github.com/shopping/go-shopping/app/product/api/internal/types"
)

type GetNewProductsLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewGetNewProductsLogic(ctx context.Context, svcCtx *svc.ServiceContext) *GetNewProductsLogic {
	return &GetNewProductsLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *GetNewProductsLogic) GetNewProducts(req *types.GetNewProductsRequest) (resp *types.Response, err error) {
	products, err := l.svcCtx.ProductModel.GetNewProducts(req.Limit)
	if err != nil {
		return nil, err
	}
	list := make([]types.ProductVO, 0, len(products))
	for _, product := range products {
		list = append(list, types.ProductVO{
			ID:            product.ID,
			Name:          product.Name,
			ProductCode:   product.ProductCode,
			CategoryID:    product.CategoryID,
			BrandID:       product.BrandID,
			MainImage:     product.MainImage,
			SubImages:     product.SubImages,
			Price:         product.Price,
			OriginalPrice: product.OriginalPrice,
			Stock:         product.Stock,
			Sales:         product.Sales,
			Unit:          product.Unit,
			Status:        product.Status,
			Brief:         product.Brief,
			Description:   product.Description,
			Keywords:      product.Keywords,
			CreatedAt:     product.CreatedAt.Format("2006-01-02 15:04:05"),
		})
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Data:      list,
		Timestamp: time.Now().Unix(),
	}, nil
}
