package obligations

import "os"

var passwordFromEnvironment = os.Getenv("PASSWORD")

// ReadNothing contains no resource acquisition or task creation.
func ReadNothing() string { return "public value" }

// ConfigureRelease is not a debug configuration signal.
func ConfigureRelease() {
	server := runtimeConfig{}
	server.SetMode(ReleaseMode)
}

type localOperations struct{}

// ReadAll is unrelated to the standard library stream operation.
func (localOperations) ReadAll() {}

// Copy is unrelated to the standard library copy operation.
func (localOperations) Copy() {}

// Next does not iterate archive entries.
func (localOperations) Next() {}

// Do does not issue a network request.
func (localOperations) Do() {}

// OpenFile does not acquire a file descriptor.
func (localOperations) OpenFile() {}

// DialContext does not establish a network connection.
func (localOperations) DialContext() {}

// UnrelatedMethods prevents method-name-only rule matching.
func UnrelatedMethods() {
	local := localOperations{}
	local.ReadAll()
	local.Copy()
	local.Next()
	local.Do()
	local.OpenFile()
	local.DialContext()
}
