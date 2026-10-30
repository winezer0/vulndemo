def projected_pair(input):
    value = source()
    return "safe", value


def projected_caller(value, box):
    first, box.field = projected_pair(value)
    sink(box.field)


def projected_nested_pair(input):
    value = source()
    return "safe", ("other", value)


def projected_nested_caller(value, box):
    first, (second, box.field) = projected_nested_pair(value)
    sink(box.field)


def projected_starred_pair(input):
    value = source()
    return "safe", (value, *others)


def projected_starred_return_caller(value, box):
    first, (second, box.field) = projected_starred_pair(value)
    sink(box.field)


def projected_starred_target_caller(value, box):
    first, (second, *rest) = projected_nested_pair(value)
    sink(rest)
