package app

import "example.com/valueorigin/model"

func FromParameter(ctx *model.Context) {
	ctx.Writer.Write(nil)
}

func FromCall() {
	ctx := model.NewContext()
	ctx.Writer.Write(nil)
}

func ThroughAlias(ctx *model.Context) {
	alias := ctx
	alias.Writer.Write(nil)
}

func FromPackageVariable() {
	model.DefaultContext.Writer.Write(nil)
}
