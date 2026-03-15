package main

import (
	"flag"
	"fmt"

	"github.com/zeromicro/go-zero/core/conf"
	"github.com/zeromicro/go-zero/rest"

	"github.com/shopping/go-shopping/app/product/api/internal/config"
	"github.com/shopping/go-shopping/app/product/api/internal/handler"
	"github.com/shopping/go-shopping/app/product/api/internal/svc"
)

var configFile = flag.String("f", "etc/product-api.yaml", "the config file")

func main() {
	flag.Parse()

	var c config.Config
	conf.MustLoad(*configFile, &c)

	server := rest.MustNewServer(c.RestConf)
	defer server.Stop()

	ctx := svc.NewServiceContext(c)
	handler.RegisterHandlers(server, ctx)

	fmt.Printf("Starting product service at %s:%d...\n", c.Host, c.Port)
	server.Start()
}
