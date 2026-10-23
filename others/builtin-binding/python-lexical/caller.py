def plain():
    len([])  # lexical:absent

def parameter(len):
    len([])  # lexical:bound

def local():
    len([])  # lexical:uninitialized
    len = 0
    len([])  # lexical:bound
    del len
    len([])  # lexical:uninitialized

def conditional(flag):
    if flag:
        len = 0
    len([])  # lexical:unknown

def comprehension(values):
    [len([]) for len in values]  # lexical:bound
    [len for len in len([])]  # lexical:absent
    [value for value in values for len in len([])]  # lexical:uninitialized
    len([])  # lexical:absent

def outer():
    len = 0
    def inner():
        nonlocal len
        len([])  # lexical:unknown

def global_read():
    global len
    len([])  # lexical:absent

class Box:
    len = 0
    def method(self):
        len([])  # lexical:absent

def mutation():
    len([])  # lexical:absent
    size = 0  # mutation binding
