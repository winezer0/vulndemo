#include <pthread.h>
#include <stddef.h>

// thread_payload returns the actual callback parameter to the join consumer.
static void *thread_payload(void *value) { return value; }
// async_callback binds creation input, actual handle and join output.
void *async_callback(void *async_callbackInput) {
    pthread_t pending = 0;
    void *async_callbackOut = NULL;
    pthread_create(&pending, NULL, thread_payload, async_callbackInput);
    pthread_join(pending, &async_callbackOut);
    return async_callbackOut;
}
// async_callback_isolation consumes the independent safe thread result.
void *async_callback_isolation(void *async_callback_isolationInput) {
    pthread_t first = 0;
    pthread_t second = 0;
    void *unused = NULL;
    void *async_callback_isolationOut = NULL;
    pthread_create(&first, NULL, thread_payload, async_callback_isolationInput);
    pthread_create(&second, NULL, thread_payload, NULL);
    pthread_join(first, &unused);
    pthread_join(second, &async_callback_isolationOut);
    return async_callback_isolationOut;
}
