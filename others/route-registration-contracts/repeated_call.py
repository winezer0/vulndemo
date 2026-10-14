if flag:
    def choose(value): return value
else:
    def choose(value, extra=None): return value
def run_choice(item):
    output = choose(item)
