package client

import "example.com/visibility/service"

func Caller() {
	service.run()
	value := service.Service{}
	value.hidden()
}
