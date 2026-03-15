package model

import (
	"context"
	"time"

	"github.com/zeromicro/go-zero/core/stores/redis"
	"gorm.io/gorm"
)

type User struct {
	ID          uint           `gorm:"primarykey" json:"id"`
	Username    string         `gorm:"uniqueIndex;size:50;not null" json:"username"`
	Password    string         `gorm:"size:255;not null" json:"-"`
	Nickname    string         `gorm:"size:100" json:"nickname"`
	Email       string         `gorm:"size:100" json:"email"`
	Phone       string         `gorm:"size:20" json:"phone"`
	Avatar      string         `gorm:"size:255" json:"avatar"`
	Gender      int            `gorm:"default:0" json:"gender"`
	Birthday    string         `gorm:"size:20" json:"birthday"`
	Status      int            `gorm:"default:0" json:"status"`
	LastLoginAt *time.Time     `json:"lastLoginAt"`
	LastLoginIP string         `gorm:"size:50" json:"lastLoginIp"`
	LoginCount  int            `gorm:"default:0" json:"loginCount"`
	CreatedAt   time.Time      `json:"createdAt"`
	UpdatedAt   time.Time      `json:"updatedAt"`
	DeletedAt   gorm.DeletedAt `gorm:"index" json:"-"`
}

type UserModel interface {
	Insert(ctx context.Context, user *User) error
	Update(ctx context.Context, user *User) error
	Delete(ctx context.Context, id uint) error
	FindOne(ctx context.Context, id uint) (*User, error)
	FindOneByUsername(ctx context.Context, username string) (*User, error)
	FindOneByEmail(ctx context.Context, email string) (*User, error)
	FindOneByPhone(ctx context.Context, phone string) (*User, error)
	ExistsByUsername(ctx context.Context, username string) (bool, error)
	ExistsByEmail(ctx context.Context, email string) (bool, error)
	ExistsByPhone(ctx context.Context, phone string) (bool, error)
}

type customUserModel struct {
	*defaultUserModel
}

type defaultUserModel struct {
	db    *gorm.DB
	cache *redis.Redis
	table string
}

func NewUserModel(db *gorm.DB, cache *redis.Redis) UserModel {
	return &customUserModel{
		defaultUserModel: &defaultUserModel{
			db:    db,
			cache: cache,
			table: "users",
		},
	}
}

func (m *defaultUserModel) Insert(ctx context.Context, user *User) error {
	return m.db.WithContext(ctx).Create(user).Error
}

func (m *defaultUserModel) Update(ctx context.Context, user *User) error {
	return m.db.WithContext(ctx).Save(user).Error
}

func (m *defaultUserModel) Delete(ctx context.Context, id uint) error {
	return m.db.WithContext(ctx).Delete(&User{}, id).Error
}

func (m *defaultUserModel) FindOne(ctx context.Context, id uint) (*User, error) {
	var user User
	err := m.db.WithContext(ctx).First(&user, id).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (m *defaultUserModel) FindOneByUsername(ctx context.Context, username string) (*User, error) {
	var user User
	err := m.db.WithContext(ctx).Where("username = ?", username).First(&user).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (m *defaultUserModel) FindOneByEmail(ctx context.Context, email string) (*User, error) {
	var user User
	err := m.db.WithContext(ctx).Where("email = ?", email).First(&user).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (m *defaultUserModel) FindOneByPhone(ctx context.Context, phone string) (*User, error) {
	var user User
	err := m.db.WithContext(ctx).Where("phone = ?", phone).First(&user).Error
	if err != nil {
		return nil, err
	}
	return &user, nil
}

func (m *defaultUserModel) ExistsByUsername(ctx context.Context, username string) (bool, error) {
	var count int64
	err := m.db.WithContext(ctx).Model(&User{}).Where("username = ?", username).Count(&count).Error
	return count > 0, err
}

func (m *defaultUserModel) ExistsByEmail(ctx context.Context, email string) (bool, error) {
	var count int64
	err := m.db.WithContext(ctx).Model(&User{}).Where("email = ?", email).Count(&count).Error
	return count > 0, err
}

func (m *defaultUserModel) ExistsByPhone(ctx context.Context, phone string) (bool, error) {
	var count int64
	err := m.db.WithContext(ctx).Model(&User{}).Where("phone = ?", phone).Count(&count).Error
	return count > 0, err
}
