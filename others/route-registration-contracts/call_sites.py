def transform(value): return value
def inner(value): return value
def outer(value): return value
def run_sites(x, y):
    a = transform(x); b = transform(y + 1)
    c = outer(inner(x))
