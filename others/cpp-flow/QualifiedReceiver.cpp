namespace api {
class Client {
public:
    int value;
    int ping() { return 1; }
};
}

int runQualified(api::Client client) {
    return client.ping();
}

int readQualified(api::Client client) {
    return client.value;
}

namespace other {
class Client {
public:
    int ping() { return 2; }
};
}

int runOther(other::Client client) {
    return client.ping();
}
