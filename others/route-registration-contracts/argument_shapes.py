def consume_shapes(first, second): pass
def run_shapes(x, y, args, kwargs):
    consume_shapes(second=x, first=y)
    consume_shapes(*args, y)
    consume_shapes(x, **kwargs)

def forward_shapes(*items: str, **options: int):
    consume_shapes(items, options)

def keyword_shapes(pos, /, normal, *, named): pass
def run_keywords(x):
    keyword_shapes(x, named=x, normal=x)
