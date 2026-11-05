package fastfixture

type Client struct{}

// Get is a local homonym with an input-independent body.
func (client *Client) Get(dst []byte, url string) (int, []byte, error) {
	return 200, []byte("local constant"), nil
}

// LocalGet must not inherit the third-party content model.
func LocalGet(homonymInput string) {
	client := &Client{}
	_, homonymOut, _ := client.Get(nil, homonymInput)
	consume(homonymOut)
}
