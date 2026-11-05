package fullvalueflow

type nestedHolder struct { Box Box; Values map[string]string }

// nested_field binds the full field path on one actual allocation.
func nested_field(nested_fieldInput string) string { holder := nestedHolder{}; holder.Box.Value = nested_fieldInput; nested_fieldOut := holder.Box.Value; return nested_fieldOut }

// nested_key binds an element beneath a field path without flattening its key.
func nested_key(nested_keyInput string) string { holder := nestedHolder{Values: map[string]string{}}; holder.Values["same"] = nested_keyInput; nested_keyOut := holder.Values["same"]; return nested_keyOut }

// nested_key_distinct keeps different exact keys beneath the same field separate.
func nested_key_distinct(nested_key_distinctInput string) string { holder := nestedHolder{Values: map[string]string{}}; holder.Values["first"] = nested_key_distinctInput; nested_key_distinctOut := holder.Values["second"]; return nested_key_distinctOut }
