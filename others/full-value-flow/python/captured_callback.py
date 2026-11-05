# captured_factory returns a callable over its actual creator argument.
def captured_factory(value):
    def captured():
        return value
    return captured

# callback_capture invokes the returned captured environment.
def callback_capture(callback_captureInput):
    selected = captured_factory(callback_captureInput)
    callback_captureOut = selected()
    return callback_captureOut

# callback_capture_isolation keeps separate factory environments apart.
def callback_capture_isolation(callback_capture_isolationInput):
    first = captured_factory(callback_capture_isolationInput)
    second = captured_factory("safe")
    first()
    callback_capture_isolationOut = second()
    return callback_capture_isolationOut
