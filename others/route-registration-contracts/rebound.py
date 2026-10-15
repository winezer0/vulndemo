import external as service

class Service:
    def execute(self): pass

def rebound():
    service = Service()
    def service(): pass
    service.execute()

def imported():
    def service(): pass
    service.execute()

def retained():
    service = Service()
    def nested(): service = Other()
    service.execute()
