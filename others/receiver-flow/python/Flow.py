def sink(value):
    return value


def run(item):
    sink(item)


class Pair:
    def update(self):
        self.left, self.right = 1, 2


def outer():
    def inner():
        sink(1)
    return inner()


class LambdaBox:
    def read(self):
        left = lambda: self.first; right = lambda: self.second
        return left(), right()


def conditional(x, flag):
    sink((x + 1) if flag else 2)


def lambda_calls():
    left = lambda x: sink(x); right = lambda y: sink(y)


def lambda_default():
    fn = lambda x=sink(1): x
    return fn()


def lambda_returns():
    direct = lambda x: x
    complex = lambda y: y + 1
    return direct, complex


def comprehension_value(outer, items):
    sink([outer for item in items])


def generator_source():
    return [1]


def generator_owner(item):
    gen = (sink(item) for item in generator_source())
    return gen


def generator_captures(items, factor, others):
    gen = (item * factor for item in items for other in others if other)
    return gen


def generator_factor(items, factor):
    gen = (sink(factor + 1) for item in items)
    return gen


def generator_local(items):
    seed = 42
    gen = (sink(seed + 1) for item in items)
    return gen


def dynamic_dispatch(table, obj, key):
    table['run']()
    getattr(obj, key)()


import asyncio
import os


class GenericBase:
    pass


def bounded[T: GenericBase, U](value: T) -> U:
    return value


async def worker():
    return 1


async def launch():
    task = asyncio.create_task(worker())
    await task


def read_first():
    return os.getenv('TOKEN')


def read_second():
    return os.getenv('TOKEN')


def publish_first():
    redis.publish('shared')


def publish_second():
    redis.publish('shared')


def publish_third():
    kafka.publish('events')


import os as environment


def read_alias():
    return environment.getenv('ALIASED_TOKEN')


def read_text():
    return "os.getenv('GHOST_TOKEN')"


def publish_named():
    pika.basic_publish(exchange='events', routing_key='jobs.created')


def publish_dynamic(key):
    pika.basic_publish(exchange='events', routing_key=key)


def read_shadow(environment):
    return environment.getenv('SHADOWED_TOKEN')
