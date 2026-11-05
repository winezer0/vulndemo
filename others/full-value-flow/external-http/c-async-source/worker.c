#include <pthread.h>
#include <stddef.h>

extern void *source(void);

static void *worker(void *unused) {
    void *insideSource = source();
    return insideSource;
}

void *run(void) {
    pthread_t handle = 0;
    void *insideOut = NULL;
    pthread_create(&handle, NULL, worker, NULL);
    pthread_join(handle, &insideOut);
    return insideOut;
}
