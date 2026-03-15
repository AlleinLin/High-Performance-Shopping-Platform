package model

import (
	"time"

	"gorm.io/gorm"
)

type Order struct {
	ID                   uint           `gorm:"primarykey" json:"id"`
	OrderNo              string         `gorm:"size:50;uniqueIndex;not null" json:"orderNo"`
	UserID               uint           `gorm:"index;not null" json:"userId"`
	TotalAmount          float64        `gorm:"type:decimal(10,2);not null" json:"totalAmount"`
	PaymentAmount        float64        `gorm:"type:decimal(10,2);not null" json:"paymentAmount"`
	DiscountAmount       float64        `gorm:"type:decimal(10,2);default:0" json:"discountAmount"`
	FreightAmount        float64        `gorm:"type:decimal(10,2);default:0" json:"freightAmount"`
	CouponID             uint           `json:"couponId"`
	CouponAmount         float64        `gorm:"type:decimal(10,2);default:0" json:"couponAmount"`
	Status               int            `gorm:"default:0;index" json:"status"`
	PaymentMethod        int            `json:"paymentMethod"`
	PaymentTime          *time.Time     `json:"paymentTime"`
	PaymentTransactionID string         `gorm:"size:100" json:"paymentTransactionId"`
	DeliveryTime         *time.Time     `json:"deliveryTime"`
	DeliveryCompany      string         `gorm:"size:50" json:"deliveryCompany"`
	TrackingNumber       string         `gorm:"size:100" json:"trackingNumber"`
	ReceiveTime          *time.Time     `json:"receiveTime"`
	ReceiverName         string         `gorm:"size:50;not null" json:"receiverName"`
	ReceiverPhone        string         `gorm:"size:20;not null" json:"receiverPhone"`
	ReceiverProvince     string         `gorm:"size:50" json:"receiverProvince"`
	ReceiverCity         string         `gorm:"size:50" json:"receiverCity"`
	ReceiverDistrict     string         `gorm:"size:50" json:"receiverDistrict"`
	ReceiverAddress      string         `gorm:"size:200;not null" json:"receiverAddress"`
	ReceiverZipCode      string         `gorm:"size:10" json:"receiverZipCode"`
	OrderType            int            `gorm:"default:0" json:"orderType"`
	SourceType           int            `gorm:"default:0" json:"sourceType"`
	Remark               string         `gorm:"size:500" json:"remark"`
	AutoConfirmDay       int            `gorm:"default:7" json:"autoConfirmDay"`
	CreatedAt            time.Time      `json:"createdAt"`
	UpdatedAt            time.Time      `json:"updatedAt"`
	DeletedAt            gorm.DeletedAt `gorm:"index" json:"-"`
	Items                []OrderItem    `gorm:"foreignKey:OrderID" json:"items"`
}

type OrderItem struct {
	ID             uint           `gorm:"primarykey" json:"id"`
	OrderID        uint           `gorm:"index;not null" json:"orderId"`
	OrderNo        string         `gorm:"size:50;index" json:"orderNo"`
	ProductID      uint           `gorm:"index;not null" json:"productId"`
	ProductName    string         `gorm:"size:200;not null" json:"productName"`
	ProductCode    string         `gorm:"size:50" json:"productCode"`
	ProductImage   string         `gorm:"size:500" json:"productImage"`
	SkuID          uint           `json:"skuId"`
	SkuName        string         `gorm:"size:200" json:"skuName"`
	SkuCode        string         `gorm:"size:50" json:"skuCode"`
	Price          float64        `gorm:"type:decimal(10,2);not null" json:"price"`
	Quantity       int            `gorm:"not null" json:"quantity"`
	TotalAmount    float64        `gorm:"type:decimal(10,2);not null" json:"totalAmount"`
	DiscountAmount float64        `gorm:"type:decimal(10,2);default:0" json:"discountAmount"`
	RealAmount     float64        `gorm:"type:decimal(10,2);not null" json:"realAmount"`
	GiftPoint      int            `gorm:"default:0" json:"giftPoint"`
	GiftGrowth     int            `gorm:"default:0" json:"giftGrowth"`
	CreatedAt      time.Time      `json:"createdAt"`
	UpdatedAt      time.Time      `json:"updatedAt"`
	DeletedAt      gorm.DeletedAt `gorm:"index" json:"-"`
}

type OrderModel interface {
	Insert(order *Order) error
	Update(order *Order) error
	Delete(id uint) error
	FindOne(id uint) (*Order, error)
	FindByOrderNo(orderNo string) (*Order, error)
	FindByUserID(userID uint, page, pageSize int, status int) ([]*Order, int64, error)
}

type defaultOrderModel struct {
	db    *gorm.DB
	table string
}

func NewOrderModel(db *gorm.DB) OrderModel {
	return &defaultOrderModel{
		db:    db,
		table: "orders",
	}
}

func (m *defaultOrderModel) Insert(order *Order) error {
	return m.db.Create(order).Error
}

func (m *defaultOrderModel) Update(order *Order) error {
	return m.db.Save(order).Error
}

func (m *defaultOrderModel) Delete(id uint) error {
	return m.db.Delete(&Order{}, id).Error
}

func (m *defaultOrderModel) FindOne(id uint) (*Order, error) {
	var order Order
	err := m.db.Preload("Items").First(&order, id).Error
	if err != nil {
		return nil, err
	}
	return &order, nil
}

func (m *defaultOrderModel) FindByOrderNo(orderNo string) (*Order, error) {
	var order Order
	err := m.db.Preload("Items").Where("order_no = ?", orderNo).First(&order).Error
	if err != nil {
		return nil, err
	}
	return &order, nil
}

func (m *defaultOrderModel) FindByUserID(userID uint, page, pageSize int, status int) ([]*Order, int64, error) {
	var orders []*Order
	var total int64

	db := m.db.Model(&Order{}).Where("user_id = ?", userID)
	if status >= 0 {
		db = db.Where("status = ?", status)
	}

	db.Count(&total)
	err := db.Preload("Items").Offset((page - 1) * pageSize).Limit(pageSize).
		Order("created_at desc").Find(&orders).Error
	return orders, total, err
}
