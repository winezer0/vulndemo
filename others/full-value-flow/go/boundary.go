package fullvalueflow

// callback_unused keeps uncalled closure bodies separate from the returned value.
func callback_unused(callback_unusedInput string) string { copyValue := func() string { return callback_unusedInput }; _ = copyValue; callback_unusedOut := "fixed"; return callback_unusedOut }

// callback_isolation distinguishes independent lexical function values.
func callback_isolation(callback_isolationInput string) string { first := func(value string) string { return value }; second := func(value string) string { return "fixed" }; _ = first; callback_isolationOut := second(callback_isolationInput); return callback_isolationOut }

// channel_distinct separates messages from different channel allocations.
func channel_distinct(channel_distinctInput string) string { first := make(chan string, 1); second := make(chan string, 1); go func() { first <- channel_distinctInput }(); second <- "fixed"; channel_distinctOut := <-second; return channel_distinctOut }

// channel_alias preserves the allocation behind a channel reference copy.
func channel_alias(channel_aliasInput string) string { first := make(chan string, 1); alias := first; go func() { alias <- channel_aliasInput }(); channel_aliasOut := <-first; return channel_aliasOut }

// cross_field_overwrite kills the old field value before the actual helper call.
func cross_field_overwrite(cross_field_overwriteInput string) string { box := &Box{}; box.Value = cross_field_overwriteInput; box.Value = "fixed"; cross_field_overwriteOut := readBox(box); return cross_field_overwriteOut }

// cross_field_distinct keeps separate allocation sites across a helper boundary.
func cross_field_distinct(cross_field_distinctInput string) string { first := &Box{}; second := &Box{}; first.Value = cross_field_distinctInput; cross_field_distinctOut := readBox(second); return cross_field_distinctOut }

// cross_field_after excludes a write that occurs after the observed call.
func cross_field_after(cross_field_afterInput string) string { box := &Box{}; cross_field_afterOut := readBox(box); box.Value = cross_field_afterInput; return cross_field_afterOut }

// composite_map binds a literal map initializer to its exact element read.
func composite_map(composite_mapInput string) string { values := map[string]string{"key": composite_mapInput}; composite_mapOut := values["key"]; return composite_mapOut }

// channel_named follows a lexical function value to its actual spawned closure.
func channel_named(channel_namedInput string) string { ch := make(chan string, 1); send := func() { ch <- channel_namedInput }; go send(); channel_namedOut := <-ch; return channel_namedOut }

// channel_parameters carries actual channel and scalar arguments into a spawned closure.
func channel_parameters(channel_parametersInput string) string { ch := make(chan string, 1); go func(target chan string, value string) { target <- value }(ch, channel_parametersInput); channel_parametersOut := <-ch; return channel_parametersOut }

// channel_parameter_distinct keeps separate channel allocations after argument binding.
func channel_parameter_distinct(channel_parameter_distinctInput string) string { first := make(chan string, 1); second := make(chan string, 1); go func(target chan string) { target <- channel_parameter_distinctInput }(first); channel_parameter_distinctOut := <-second; return channel_parameter_distinctOut }
