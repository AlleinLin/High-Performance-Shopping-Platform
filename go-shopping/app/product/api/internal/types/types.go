package types

import "time"

type ProductVO struct {
	ID            uint    `json:"id"`
	Name          string  `json:"name"`
	ProductCode   string  `json:"productCode"`
	CategoryID    uint    `json:"categoryId"`
	BrandID       uint    `json:"brandId"`
	MainImage     string  `json:"mainImage"`
	SubImages     string  `json:"subImages"`
	Price         float64 `json:"price"`
	OriginalPrice float64 `json:"originalPrice"`
	Stock         int     `json:"stock"`
	Sales         int     `json:"sales"`
	Unit          string  `json:"unit"`
	Status        int     `json:"status"`
	Brief         string  `json:"brief"`
	Description   string  `json:"description"`
	Keywords      string  `json:"keywords"`
	CreatedAt     string  `json:"createdAt"`
}

type GetProductRequest struct {
	ID uint `path:"id"`
}

type GetProductListRequest struct {
	Page     int `form:"page,default=1"`
	PageSize int `form:"pageSize,default=10"`
}

type GetProductByCategoryRequest struct {
	ID       uint `path:"id"`
	Page     int  `form:"page,default=1"`
	PageSize int  `form:"pageSize,default=10"`
}

type SearchProductRequest struct {
	Keyword  string `form:"keyword"`
	Page     int    `form:"page,default=1"`
	PageSize int    `form:"pageSize,default=10"`
}

type GetHotProductsRequest struct {
	Limit int `form:"limit,default=10"`
}

type GetNewProductsRequest struct {
	Limit int `form:"limit,default=10"`
}

type CreateProductRequest struct {
	Name          string  `json:"name"`
	ProductCode   string  `json:"productCode"`
	CategoryID    uint    `json:"categoryId"`
	BrandID       uint    `json:"brandId"`
	MainImage     string  `json:"mainImage"`
	SubImages     string  `json:"subImages"`
	Price         float64 `json:"price"`
	OriginalPrice float64 `json:"originalPrice"`
	Stock         int     `json:"stock"`
	Unit          string  `json:"unit"`
	Brief         string  `json:"brief"`
	Description   string  `json:"description"`
	Keywords      string  `json:"keywords"`
}

type UpdateProductRequest struct {
	ID            uint    `path:"id"`
	Name          string  `json:"name"`
	CategoryID    uint    `json:"categoryId"`
	BrandID       uint    `json:"brandId"`
	MainImage     string  `json:"mainImage"`
	SubImages     string  `json:"subImages"`
	Price         float64 `json:"price"`
	OriginalPrice float64 `json:"originalPrice"`
	Stock         int     `json:"stock"`
	Unit          string  `json:"unit"`
	Status        int     `json:"status"`
	Brief         string  `json:"brief"`
	Description   string  `json:"description"`
	Keywords      string  `json:"keywords"`
}

type DeleteProductRequest struct {
	ID uint `path:"id"`
}

type Response struct {
	Code      int         `json:"code"`
	Message   string      `json:"message"`
	Data      interface{} `json:"data,omitempty"`
	Timestamp int64       `json:"timestamp"`
}

type PageResponse struct {
	List     interface{} `json:"list"`
	Total    int64       `json:"total"`
	Page     int         `json:"page"`
	PageSize int         `json:"pageSize"`
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
