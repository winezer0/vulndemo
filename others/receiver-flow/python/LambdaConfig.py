import os

def make_reader():
    return lambda: (os.getenv("TOKEN"), websocket_connect("/upstream"), websocket("/client"))
