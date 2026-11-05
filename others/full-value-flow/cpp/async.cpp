#include <future>

// async_callback consumes the return of its own scheduled closure.
const char *async_callback(const char *async_callbackInput) {
    auto produce = [async_callbackInput]() { return async_callbackInput; };
    std::future<const char *> pending = std::async(produce);
    const char *async_callbackOut = pending.get();
    return async_callbackOut;
}
// async_callback_isolation keeps independent future instances separate.
const char *async_callback_isolation(const char *async_callback_isolationInput) {
    auto tainted = [async_callback_isolationInput]() { return async_callback_isolationInput; };
    auto safe = []() { return "safe"; };
    std::future<const char *> first = std::async(tainted);
    std::future<const char *> second = std::async(safe);
    first.get();
    const char *async_callback_isolationOut = second.get();
    return async_callback_isolationOut;
}
