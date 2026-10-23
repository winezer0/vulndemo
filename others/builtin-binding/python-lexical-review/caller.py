def checked():
    obj.len()
    global len
    len('1')  # lexical:absent

def outer():
    len = replacement
    def checked_nonlocal():
        obj.len()
        nonlocal len
        len('1')  # lexical:unknown

def pattern_label(value):
    match value:
        case C(len=other):
            pass
    global len
    len('1')  # lexical:absent
