package sample

import (
	v "github.com/spf13/viper"
	env "os"
)

type configClient struct{}

func (configClient) Get(string) string { return "" }

func readConfig(client configClient) string {
	_ = v.GetString("real.key")
	_ = env.Getenv("PORT")
	return client.Get("not.config")
}
