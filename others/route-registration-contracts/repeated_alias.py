import first as api
def before_import():
    api.execute()
import second as api
def after_import():
    api.execute()
from first import older as action
def before_named():
    action()
from second import newer as action
def after_named():
    action()
