class RepeatedClient:
    def child(self) -> Child: pass
    def child(self) -> Other: pass

def repeated_method():
    client = RepeatedClient()
    client.child().execute()

def rewritten(first: A) -> First:
    sink(first)

def rewritten(second: B, extra: C) -> Second:
    sink(second)
