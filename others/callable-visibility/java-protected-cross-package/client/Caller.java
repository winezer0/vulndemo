package client;

import service.Service;

class Caller {
    void call() { new Service().run(); }
}
