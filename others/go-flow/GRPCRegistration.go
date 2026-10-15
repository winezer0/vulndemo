package sample

import pb "example.com/proto"

func registerGRPC(server any, implementation any) {
	pb.RegisterUserServer(server, implementation)
}
