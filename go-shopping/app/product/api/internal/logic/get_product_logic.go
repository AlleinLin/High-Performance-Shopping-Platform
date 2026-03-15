package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/product/api/internal/svc"
	"github.com/shopping/go-shopping/app/product/api/internal/types"
)

type GetProductLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewGetProductLogic(ctx context.Context, svcCtx *svc.ServiceContext) *GetProductLogic {
	return &GetProductLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *GetProductLogic) GetProduct(req *types.GetProductRequest) (resp *types.Response, err error) {
	product, err := l.svcCtx.ProductModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	result := types.ProductVO{
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
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Data:      result,
		Timestamp: time.Now().Unix(),
	}, nil
}
