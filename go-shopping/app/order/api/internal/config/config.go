package config

import "github.com/zeromicro/go-zero/rest"

type Config struct {
	rest.RestConf
	Auth struct {
		AccessSecret string
		AccessExpire int64
	}
	MySQL struct {
		Host     string
		Port     int
		User     string
		Password string
		DBName   string
	}
	Redis struct {
		Host     string
		Port     int
		Password string
		DB       int
	}
}
