package logic

import (
	"context"
	"time"

	"github.com/shopping/go-shopping/app/product/api/internal/svc"
	"github.com/shopping/go-shopping/app/product/api/internal/types"
	"github.com/shopping/go-shopping/app/product/model"
)

type CreateProductLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
}

func NewCreateProductLogic(ctx context.Context, svcCtx *svc.ServiceContext) *CreateProductLogic {
	return &CreateProductLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
	}
}

func (l *CreateProductLogic) CreateProduct(req *types.CreateProductRequest) (resp *types.Response, err error) {
	product := &model.Product{
		Name:          req.Name,
		ProductCode:   req.ProductCode,
		CategoryID:    req.CategoryID,
		BrandID:       req.BrandID,
		MainImage:     req.MainImage,
		SubImages:     req.SubImages,
		Price:         req.Price,
		OriginalPrice: req.OriginalPrice,
		Stock:         req.Stock,
		Unit:          req.Unit,
		Brief:         req.Brief,
		Description:   req.Description,
		Keywords:      req.Keywords,
		Status:        1,
	}
	if err := l.svcCtx.ProductModel.Insert(product); err != nil {
		return nil, err
	}
	return &types.Response{
		Code:      200,
		Message:   "success",
		Data:      product.ID,
		Timestamp: time.Now().Unix(),
	}, nil
}
