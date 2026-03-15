package model

import (
	"time"

	"gorm.io/gorm"
)

type Product struct {
	ID            uint           `gorm:"primarykey" json:"id"`
	Name          string         `gorm:"size:200;not null;index" json:"name"`
	ProductCode   string         `gorm:"size:50;uniqueIndex" json:"productCode"`
	CategoryID    uint           `gorm:"index" json:"categoryId"`
	BrandID       uint           `gorm:"index" json:"brandId"`
	MainImage     string         `gorm:"size:500" json:"mainImage"`
	SubImages     string         `gorm:"type:text" json:"subImages"`
	Price         float64        `gorm:"type:decimal(10,2);not null" json:"price"`
	OriginalPrice float64        `gorm:"type:decimal(10,2)" json:"originalPrice"`
	Stock         int            `gorm:"default:0" json:"stock"`
	LowStock      int            `gorm:"default:10" json:"lowStock"`
	Sales         int            `gorm:"default:0" json:"sales"`
	Unit          string         `gorm:"size:20" json:"unit"`
	Weight        float64        `gorm:"type:decimal(10,2)" json:"weight"`
	Sort          int            `gorm:"default:0" json:"sort"`
	Status        int            `gorm:"default:0;index" json:"status"`
	Brief         string         `gorm:"size:500" json:"brief"`
	Description   string         `gorm:"type:longtext" json:"description"`
	Keywords      string         `gorm:"size:200" json:"keywords"`
	CreatedAt     time.Time      `json:"createdAt"`
	UpdatedAt     time.Time      `json:"updatedAt"`
	DeletedAt     gorm.DeletedAt `gorm:"index" json:"-"`
}

type Category struct {
	ID          uint           `gorm:"primarykey" json:"id"`
	ParentID    uint           `gorm:"index" json:"parentId"`
	Name        string         `gorm:"size:50;not null" json:"name"`
	Level       int            `gorm:"default:1" json:"level"`
	Icon        string         `gorm:"size:200" json:"icon"`
	Image       string         `gorm:"size:500" json:"image"`
	Sort        int            `gorm:"default:0" json:"sort"`
	ShowStatus  bool           `gorm:"default:true" json:"showStatus"`
	Keywords    string         `gorm:"size:200" json:"keywords"`
	Description string         `gorm:"size:500" json:"description"`
	CreatedAt   time.Time      `json:"createdAt"`
	UpdatedAt   time.Time      `json:"updatedAt"`
	DeletedAt   gorm.DeletedAt `gorm:"index" json:"-"`
}

type Brand struct {
	ID            uint           `gorm:"primarykey" json:"id"`
	Name          string         `gorm:"size:100;not null" json:"name"`
	FirstLetter   string         `gorm:"size:1" json:"firstLetter"`
	Logo          string         `gorm:"size:500" json:"logo"`
	Description   string         `gorm:"size:500" json:"description"`
	Sort          int            `gorm:"default:0" json:"sort"`
	ShowStatus    bool           `gorm:"default:true" json:"showStatus"`
	FactoryStatus int            `gorm:"default:0" json:"factoryStatus"`
	CreatedAt     time.Time      `json:"createdAt"`
	UpdatedAt     time.Time      `json:"updatedAt"`
	DeletedAt     gorm.DeletedAt `gorm:"index" json:"-"`
}

type ProductModel interface {
	Insert(product *Product) error
	Update(product *Product) error
	Delete(id uint) error
	FindOne(id uint) (*Product, error)
	FindByCategory(categoryID uint, page, pageSize int) ([]*Product, int64, error)
	Search(keyword string, page, pageSize int) ([]*Product, int64, error)
	GetHotProducts(limit int) ([]*Product, error)
	GetNewProducts(limit int) ([]*Product, error)
	DeductStock(id uint, quantity int) error
	AddStock(id uint, quantity int) error
}

type defaultProductModel struct {
	db    *gorm.DB
	table string
}

func NewProductModel(db *gorm.DB) ProductModel {
	return &defaultProductModel{
		db:    db,
		table: "products",
	}
}

func (m *defaultProductModel) Insert(product *Product) error {
	return m.db.Create(product).Error
}

func (m *defaultProductModel) Update(product *Product) error {
	return m.db.Save(product).Error
}

func (m *defaultProductModel) Delete(id uint) error {
	return m.db.Delete(&Product{}, id).Error
}

func (m *defaultProductModel) FindOne(id uint) (*Product, error) {
	var product Product
	err := m.db.First(&product, id).Error
	if err != nil {
		return nil, err
	}
	return &product, nil
}

func (m *defaultProductModel) FindByCategory(categoryID uint, page, pageSize int) ([]*Product, int64, error) {
	var products []*Product
	var total int64

	db := m.db.Model(&Product{}).Where("category_id = ? AND status = 2", categoryID)
	db.Count(&total)

	err := db.Offset((page - 1) * pageSize).Limit(pageSize).Order("sort desc, created_at desc").Find(&products).Error
	return products, total, err
}

func (m *defaultProductModel) Search(keyword string, page, pageSize int) ([]*Product, int64, error) {
	var products []*Product
	var total int64

	db := m.db.Model(&Product{}).Where("status = 2")
	if keyword != "" {
		db = db.Where("name LIKE ? OR keywords LIKE ? OR brief LIKE ?", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%")
	}

	db.Count(&total)
	err := db.Offset((page - 1) * pageSize).Limit(pageSize).Order("sales desc, created_at desc").Find(&products).Error
	return products, total, err
}

func (m *defaultProductModel) GetHotProducts(limit int) ([]*Product, error) {
	var products []*Product
	err := m.db.Where("status = 2").Order("sales desc").Limit(limit).Find(&products).Error
	return products, err
}

func (m *defaultProductModel) GetNewProducts(limit int) ([]*Product, error) {
	var products []*Product
	err := m.db.Where("status = 2").Order("created_at desc").Limit(limit).Find(&products).Error
	return products, err
}

func (m *defaultProductModel) DeductStock(id uint, quantity int) error {
	return m.db.Model(&Product{}).Where("id = ? AND stock >= ?", id, quantity).
		UpdateColumn("stock", gorm.Expr("stock - ?", quantity)).Error
}

func (m *defaultProductModel) AddStock(id uint, quantity int) error {
	return m.db.Model(&Product{}).Where("id = ?", id).
		UpdateColumn("stock", gorm.Expr("stock + ?", quantity)).Error
}
