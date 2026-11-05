package fullvalueflow

type Box struct{ Value string }

// identity supplies a source baseline scenario.
func identity(value string) string { return value }

// direct supplies a source baseline scenario.
func direct(directInput string) string {
	directOut := directInput
	return directOut
}

// field_same supplies a source baseline scenario.
func field_same(field_sameInput string) string {
	box := &Box{}
	box.Value = field_sameInput
	field_sameOut := box.Value
	return field_sameOut
}

// field_distinct supplies a source baseline scenario.
func field_distinct(field_distinctInput string) string {
	a, b := &Box{}, &Box{}
	a.Value = field_distinctInput
	field_distinctOut := b.Value
	return field_distinctOut
}

// map_same supplies a source baseline scenario.
func map_same(map_sameInput string) string {
	values := map[string]string{}
	values["key"] = map_sameInput
	map_sameOut := values["key"]
	return map_sameOut
}

// map_distinct_key supplies a source baseline scenario.
func map_distinct_key(map_distinct_keyInput string) string {
	values := map[string]string{}
	values["a"] = map_distinct_keyInput
	map_distinct_keyOut := values["b"]
	return map_distinct_keyOut
}

// map_distinct_object supplies a source baseline scenario.
func map_distinct_object(map_distinct_objectInput string) string {
	a, b := map[string]string{}, map[string]string{}
	a["key"] = map_distinct_objectInput
	map_distinct_objectOut := b["key"]
	return map_distinct_objectOut
}

// map_dynamic supplies a source baseline scenario.
func map_dynamic(map_dynamicInput string, key, readKey string) string {
	values := map[string]string{}
	values[key] = map_dynamicInput
	map_dynamicOut := values[readKey]
	return map_dynamicOut
}

// map_empty_key supplies a source baseline scenario.
func map_empty_key(map_empty_keyInput string) string {
	values := map[string]string{}
	values[""] = map_empty_keyInput
	map_empty_keyOut := values[""]
	return map_empty_keyOut
}

// slice_same supplies a source baseline scenario.
func slice_same(slice_sameInput string) string {
	values := make([]string, 2)
	values[0] = slice_sameInput
	slice_sameOut := values[0]
	return slice_sameOut
}

// array_distinct supplies a source baseline scenario.
func array_distinct(array_distinctInput string) string {
	var values [2]string
	values[0] = array_distinctInput
	array_distinctOut := values[1]
	return array_distinctOut
}

// alias supplies a source baseline scenario.
func alias(aliasInput string) string {
	a := &Box{}
	b := a
	a.Value = aliasInput
	aliasOut := b.Value
	return aliasOut
}

// cross_return supplies a source baseline scenario.
func cross_return(cross_returnInput string) string {
	cross_returnOut := identity(cross_returnInput)
	return cross_returnOut
}

// range_value supplies a source baseline scenario.
func range_value(range_valueInput string) string {
	values := []string{range_valueInput}
	accumulated := ""
	for _, item := range values {
		accumulated = item
	}
	range_valueOut := accumulated; return range_valueOut
}

// overwrite supplies a source baseline scenario.
func overwrite(overwriteInput string) string {
	box := &Box{}
	box.Value = overwriteInput
	box.Value = "fixed"
	overwriteOut := box.Value
	return overwriteOut
}

// callback supplies a source baseline scenario.
func callback(callbackInput string) string {
	copyValue := func(value string) string { return value }
	callbackOut := copyValue(callbackInput)
	return callbackOut
}

// async_channel supplies a source baseline scenario.
func async_channel(async_channelInput string) string {
	ch := make(chan string, 1)
	go func() { ch <- async_channelInput }()
	async_channelOut := <-ch
	return async_channelOut
}

// readBox returns the actual field value.
func readBox(box *Box) string { return box.Value }

// second preserves only its second parameter.
func second(first, last string) string { return last }

// externalRead represents a body unavailable to the analysis.
func externalRead(box *Box) string

// array_same supplies a source baseline scenario.
func array_same(array_sameInput string) string {
	var values [2]string
	values[0] = array_sameInput
	array_sameOut := values[0]
	return array_sameOut
}

// slice_alias supplies a source baseline scenario.
func slice_alias(slice_aliasInput string) string {
	values := make([]string, 1)
	alias := values[:]
	values[0] = slice_aliasInput
	slice_aliasOut := alias[0]
	return slice_aliasOut
}

// branch supplies a source baseline scenario.
func branch(branchInput string, flag bool) string {
	box := &Box{}
	if flag {
		box.Value = branchInput
	}
	branchOut := box.Value
	return branchOut
}

// cross_field supplies a source baseline scenario.
func cross_field(cross_fieldInput string) string {
	box := &Box{}
	box.Value = cross_fieldInput
	cross_fieldOut := readBox(box)
	return cross_fieldOut
}

// call_isolation supplies a source baseline scenario.
func call_isolation(call_isolationInput string) string {
	_ = identity(call_isolationInput)
	call_isolationOut := identity("fixed")
	return call_isolationOut
}

// slot_positive supplies a source baseline scenario.
func slot_positive(slot_positiveInput string) string {
	slot_positiveOut := second("fixed", slot_positiveInput)
	return slot_positiveOut
}

// slot_negative supplies a source baseline scenario.
func slot_negative(slot_negativeInput string) string {
	slot_negativeOut := second(slot_negativeInput, "fixed")
	return slot_negativeOut
}

// opaque supplies a source baseline scenario.
func opaque(opaqueInput string) string {
	box := &Box{}
	box.Value = opaqueInput
	opaqueOut := externalRead(box)
	return opaqueOut
}
