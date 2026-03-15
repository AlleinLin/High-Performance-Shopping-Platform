package svc

import (
	"fmt"

	"github.com/zeromicro/go-zero/core/stores/redis"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"

	"github.com/shopping/go-shopping/app/order/api/internal/config"
	"github.com/shopping/go-shopping/app/order/model"
)

type ServiceContext struct {
	Config     config.Config
	DB         *gorm.DB
	Redis      *redis.Redis
	OrderModel model.OrderModel
}

func NewServiceContext(c config.Config) *ServiceContext {
	dsn := fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=utf8mb4&parseTime=True&loc=Local",
		c.MySQL.User,
		c.MySQL.Password,
		c.MySQL.Host,
		c.MySQL.Port,
		c.MySQL.DBName,
	)

	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})
	if err != nil {
		panic(err)
	}

	db.AutoMigrate(&model.Order{}, &model.OrderItem{})

	rdb := redis.MustNewRedis(redis.RedisConf{
		Host: fmt.Sprintf("%s:%d", c.Redis.Host, c.Redis.Port),
		Type: "node",
		Pass: c.Redis.Password,
	})

	return &ServiceContext{
		Config:     c,
		DB:         db,
		Redis:      rdb,
		OrderModel: model.NewOrderModel(db),
	}
}
