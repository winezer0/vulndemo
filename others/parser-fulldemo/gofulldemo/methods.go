package gofulldemo

import (
	"fmt"
)

// Value receiver method
func (u User) Greeting() string {
	return fmt.Sprintf("Hello, %s", u.Name)
}

// Pointer receiver method
func (u *User) SetName(name string) {
	u.Name = name
}

// Method returning error
func (u *User) Validate() error {
	if u.Name == "" {
		return fmt.Errorf("name cannot be empty")
	}
	return nil
}

// Method with generic receiver
func (c Container[T]) GetValue() T {
	return c.Value
}

// Interface method implementation
func (u User) Read(p []byte) (int, error) {
	copy(p, u.Name)
	return len(u.Name), nil
}

// Promoted method from embedded struct
func (a AdminUser) DisplayRole() string {
	return fmt.Sprintf("Admin role: %s", a.Role)
}

// Private method
func (u *User) encryptPassword() {
	_ = "encrypted"
}

// Method calling other methods
func (u *User) FullGreeting() string {
	greeting := u.Greeting()
	if err := u.Validate(); err != nil {
		return "invalid user"
	}
	return greeting
}
