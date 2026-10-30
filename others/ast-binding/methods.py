def sink(value):
    pass


static_alias = staticmethod


def custom(method):
    return method


class Service:
    def method(self, value):
        sink(value)
        sink(self.field)

    @staticmethod
    def static(value):
        sink(value)

    @static_alias
    def alias(value):
        sink(value)

    @classmethod
    def factory(owner, value):
        sink(value)

    @custom
    def decorated(self, value):
        sink(value)


def bound_caller(input, service: Service):
    value = input
    service.method(value)


def unbound_caller(input, service: Service):
    value = input
    Service.method(service, value)


def tuple_caller(input, service: Service):
    value = input
    service.method(*[value])


def receiver_caller(input: Service):
    value = input
    value.method("safe")


def static_caller(input):
    value = input
    Service.static(value)


def alias_caller(input):
    value = input
    Service.alias(value)


def class_caller(input):
    value = input
    Service.factory(value)


def mapping_caller(input, service: Service):
    value = input
    service.method(**{"value": value})


def unbound_mapping_caller(input, service: Service):
    value = input
    Service.method(service, **{"value": value})


def static_mapping_caller(input):
    value = input
    Service.static(**{"value": value})


def class_mapping_caller(input):
    value = input
    Service.factory(**{"value": value})


def conflicting_mapping_caller(input, service: Service):
    value = input
    service.method("safe", **{"value": value})


def receiver_mapping_conflict(input, service: Service):
    value = input
    service.method(**{"self": "safe", "value": value})


def custom_decorator_caller(input, service: Service):
    value = input
    service.decorated(value)
