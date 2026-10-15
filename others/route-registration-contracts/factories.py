def make() -> Product:
    pass

def known_factory():
    make().execute()

def shadowed_factory(make):
    make().execute()

def replaced_factory():
    make = external_factory
    make().execute()
