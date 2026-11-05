# captured_mutation_factory retains a mutable lexical cell across callable creation.
def callback_capture_local(callback_capture_localInput):
    # Invoke a closure in its owning scope.
    current = callback_capture_localInput
    def selected():
        return current
    callback_capture_localOut = selected()
    return callback_capture_localOut

def callback_capture_local_isolation(callback_capture_local_isolationInput):
    # Preserve independent captured values.
    current = callback_capture_local_isolationInput
    def first():
        return current
    safe = "safe"
    def second():
        return safe
    first()
    callback_capture_local_isolationOut = second()
    return callback_capture_local_isolationOut

def captured_mutation_factory(value):
    current = "safe"
    def selected():
        return current
    current = value
    return selected

# callback_capture_write observes a write made after callable creation.
def callback_capture_write(callback_capture_writeInput):
    selected = captured_mutation_factory(callback_capture_writeInput)
    callback_capture_writeOut = selected()
    return callback_capture_writeOut
