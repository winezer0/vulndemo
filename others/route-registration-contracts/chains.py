class Child:
    def execute(self): pass

class Client:
    def child(self) -> Child: pass

def known_chain():
    client = Client()
    client.child().execute()

def unknown_chain(client):
    client.child().execute()

def replaced_chain():
    client = Client()
    client = unknown_factory()
    client.child().execute()
