
def source():
    return "value"

def sink(value):
    return value

def driver(value):
    direct = value
    complex_value = value + "!"
    return sink(complex_value)
