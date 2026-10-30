def source():
    return "input"


def sink(value):
    pass


def target(first, *, second):
    sink(second)
    sink(first)


def sequence_target(first, second):
    sink(second)


def caller(input):
    value = input
    target("safe", second=value)


def sequence_spread_caller(input, parts):
    value = input
    sequence_target(*parts, value)


def mapping_caller(input):
    value = input
    target(**{"second": value, "first": "safe"})


def mapping_overwritten(input):
    value = input
    target("safe", **{"second": value, "second": "safe"})


def mapping_last(input):
    value = input
    target("safe", **{"second": "safe", "second": value})


def mapping_conflict(input):
    value = input
    target("safe", second=value, **{"second": "safe"})


def mapping_dynamic(input, key):
    value = input
    target("safe", **{key: value})


def mapping_nested(input, extra):
    value = input
    target("safe", **{"second": value, **extra})


def mapping_mixed(input):
    value = input
    target(*["safe"], **{"second": value})


def rebound_caller(input, replacement):
    value = input
    target = replacement
    target("safe", second=value)


def unpacked(values):
    target(*values)


def return_target():
    value = source()
    return value


def parenthesized_return_caller():
    second = ((return_target()))
    sink(second)


def duplicate(input):
    target(input, first=input, second="safe")


def wrapped_return_target():
    value = source()
    return ((value))


def wrapped_return_caller():
    second = wrapped_return_target()
    sink(second)


def transformed_return_target():
    value = source()
    return ((value + "suffix"))


def transformed_return_caller():
    second = transformed_return_target()
    sink(second)


def tuple_return_target():
    value = source()
    return ((value,))


def tuple_return_caller():
    second = tuple_return_target()
    sink(second)
