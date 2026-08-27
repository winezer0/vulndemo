package gofulldemo

import (
	"context"
	"database/sql"
	"errors"
	"fmt"
	"log"
	"net/http"
	"os"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/nats-io/nats.go"
	"github.com/supabase-community/supabase-go"
	"go.uber.org/zap"
	"google.golang.org/grpc"
)

// GinRouteDemo demonstrates Gin route registration
func GinRouteDemo(r *gin.Engine) {
	r.GET("/api/users", listUsers)
	r.POST("/api/users", createUser)
	r.PUT("/api/users/:id", updateUser)
	r.DELETE("/api/users/:id", deleteUser)
}

func listUsers(c *gin.Context)  { c.JSON(200, nil) }
func createUser(c *gin.Context) { c.JSON(201, nil) }
func updateUser(c *gin.Context) { c.JSON(200, nil) }
func deleteUser(c *gin.Context) { c.JSON(204, nil) }

// GinMiddleware demonstrates Gin middleware
func GinMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Next()
	}
}

// GRPCDemo demonstrates gRPC calls
//
//nolint:staticcheck
func GRPCDemo(conn *grpc.ClientConn) {
	// Simulate gRPC stub calls
	// In real scenarios, use protoc-generated stub code
	client := NewUserServiceClient(conn)
	user, err := client.GetUser(context.Background(), &GetUserRequest{Id: 1})
	if err != nil {
		log.Fatal(err)
	}
	_ = user
}

// UserServiceClient is a gRPC stub type
type UserServiceClient struct{}

func NewUserServiceClient(cc *grpc.ClientConn) *UserServiceClient {
	return &UserServiceClient{}
}

func (c *UserServiceClient) GetUser(ctx context.Context, req *GetUserRequest) (*GetUserResponse, error) {
	return nil, nil
}

type GetUserRequest struct {
	Id int64
}
type GetUserResponse struct {
	Name string
}

// SQLDemo demonstrates SQL calls (Supabase RPC)
func SQLDemo(client *supabase.Client) {
	client.rpc("get_users", map[string]any{})
	client.rpc("get_user_by_id", map[string]any{"id": 1})
}

// SQLDatabaseSQL demonstrates database/sql usage
func SQLDatabaseSQL() {
	db, _ := sql.Open("postgres", "")
	rows, _ := db.Query("SELECT id, name FROM users WHERE active = $1", true)
	_ = rows
}

// PubSubNats demonstrates NATS publish/subscribe
func PubSubNats(nc *nats.Conn) {
	// Publish
	nc.Publish("orders.created", []byte(`{"id":1}`))

	// Subscribe
	nc.Subscribe("orders.created", func(msg *nats.Msg) {
		fmt.Println("received:", string(msg.Data))
	})
}

// ConfigKeyDemo demonstrates configuration key reading
func ConfigKeyDemo() {
	// Environment variables
	dbHost := os.Getenv("DB_HOST")
	dbPort := os.Getenv("DB_PORT")

	// Viper-style config retrieval
	_ = dbHost
	_ = dbPort
}

// ObservabilityDemo demonstrates observability
func ObservabilityDemo() {
	// Logging
	log.Println("server starting")
	log.Printf("user %s logged in", "admin")

	// Zap logging
	logger, _ := zap.NewProduction()
	logger.Info("user_action",
		zap.String("action", "login"),
		zap.Int("user_id", 42),
	)
	_ = logger
}

// ObservabilityWithReturn demonstrates observability with return value
func ObservabilityWithReturn() error {
	log.Printf("processing request")
	return errors.New("processing failed")
}

// StringMetricDemo demonstrates metric strings
func StringMetricDemo() {
	_ = "http_requests_total"
	_ = "db_query_duration_seconds"
}

// WebsocketUpgrade demonstrates WebSocket upgrade
func WebsocketUpgrade(w http.ResponseWriter, r *http.Request) {
	// Simulate WebSocket upgrade
	// hijacker := w.(http.Hijacker)
	_ = w
	_ = r
}

// FlagsOrFeatureCheck demonstrates feature flag detection
func FlagsOrFeatureCheck() {
	// Simulate feature toggle check
	if os.Getenv("FEATURE_NEW_UI") == "true" {
		fmt.Println("new UI enabled")
	}
}

// KeyedElementStruct demonstrates keyed element struct literal
type StatusInfo struct {
	Code    int
	Message string
}

// KeyedElementRef demonstrates keyed element reference
func KeyedElementRef(si StatusInfo) StatusInfo {
	return StatusInfo{
		Code:    si.Code,
		Message: si.Message,
	}
}

// KeyedElementString demonstrates keyed element string
func KeyedElementString() StatusInfo {
	return StatusInfo{
		Code:    200,
		Message: "OK",
	}
}

// KeyedElementSelector demonstrates keyed element selector
type ExtraInfo struct {
	StatusCode int
	StatusText string
}

func KeyedElementSelector(ei ExtraInfo) StatusInfo {
	return StatusInfo{
		Code:    ei.StatusCode,
		Message: ei.StatusText,
	}
}

// ORMModel demonstrates an ORM model
type Product struct {
	ID    int64  `gorm:"primaryKey"`
	Name  string `gorm:"column:product_name"`
	Price float64
}

// TableName demonstrates ORM custom table name
func (Product) TableName() string {
	return "shop_products"
}

// TimeValue demonstrates time.Time literal
func TimeValue() time.Time {
	return time.Date(2024, 1, 1, 0, 0, 0, 0, time.UTC)
}

// ChanType demonstrates channel type reference
func ChanType() chan int {
	return make(chan int, 5)
}

// GoGeneratePragma demonstrates go:generate directive
//go:generate echo "generated"

func GoGenerateDemo() {}
