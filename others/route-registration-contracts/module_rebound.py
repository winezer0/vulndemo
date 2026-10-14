import external as service
service = unknown_factory()

def module_rebound():
    service.execute()

import external as service

def module_restored():
    service.execute()
