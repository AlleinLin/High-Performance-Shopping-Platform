package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/product/api/internal/svc"
	"github.com/shopping/go-shopping/app/product/api/internal/types"
)

type UpdateProductLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewUpdateProductLogic(ctx context.Context, svcCtx *svc.ServiceContext) *UpdateProductLogic {
	return &UpdateProductLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *UpdateProductLogic) UpdateProduct(req *types.UpdateProductRequest) (resp *types.Response, err error) {
	product, err := l.svcCtx.ProductModel.FindOne(req.ID)
	if err != nil {
		return nil, err
	}
	product.Name = req.Name
	product.CategoryID = req.CategoryID
	product.BrandID = req.BrandID
	product.MainImage = req.MainImage
	product.SubImages = req.SubImages
	product.Price = req.Price
	product.OriginalPrice = req.OriginalPrice
	product.Stock = req.Stock
	product.Unit = req.Unit
	product.Status = req.Status
	product.Brief = req.Brief
	product.Description = req.Description
	product.Keywords = req.Keywords
	if err := l.svcCtx.ProductModel.Update(product); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Timestamp: time.Now().Unix(),
	}, nil
}
