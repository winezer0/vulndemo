#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <curl/curl.h>

struct Buffer { char data[128]; size_t size; size_t limit; };
static int checks = 0;
static int fixture_consumed = 0;
extern void easy_userdata(const char *input);
extern void easy_uninitialized(const char *input);
extern void easy_struct(const char *input);
extern void easy_choice(const char *input, int choose);
extern void easy_reuse(const char *first, const char *second);
extern void easy_separate(const char *first, const char *second);

// check rejects mismatched SDK behavior and counts the executed checks.
static void check(int condition, const char *message) {
    ++checks;
    if (!condition) { fprintf(stderr, "%s\n", message); exit(1); }
}

// consume verifies the actual graph fixture's reads before and after curl execution.
void consume(const char *value) {
    ++fixture_consumed;
    if (fixture_consumed == 14 || fixture_consumed == 16) {
        check(value[0] == 's', "fixture second response was not isolated");
    } else if (fixture_consumed == 2 || fixture_consumed == 4 || fixture_consumed == 5 || fixture_consumed == 8 || fixture_consumed == 10 || fixture_consumed == 13 || fixture_consumed == 15) {
        check(value[0] == 'f', "fixture userdata callback did not write the response");
    } else {
        check(value[0] == '\0', "fixture early or independent buffer changed");
    }
}

// write_payload retains only the actual written prefix and reports short writes.
static size_t write_payload(char *data, size_t size, size_t count, void *userdata) {
    struct Buffer *buffer = (struct Buffer *)userdata;
    if (size != 0 && count > SIZE_MAX / size) return 0;
    size_t bytes = size * count;
    size_t remaining = buffer->limit - buffer->size;
    size_t copied = bytes < remaining ? bytes : remaining;
    memcpy(buffer->data + buffer->size, data, copied);
    buffer->size += copied;
    buffer->data[buffer->size] = '\0';
    return copied;
}

// configure binds the actual handle, callback and userdata before execution.
static void configure(CURL *handle, struct Buffer *buffer, const char *url) {
    check(handle != NULL, "curl handle allocation failed");
    check(curl_easy_setopt(handle, CURLOPT_URL, url) == CURLE_OK, "URL configuration failed");
    check(curl_easy_setopt(handle, CURLOPT_TIMEOUT, 5L) == CURLE_OK, "timeout configuration failed");
    check(curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, write_payload) == CURLE_OK, "callback configuration failed");
    check(curl_easy_setopt(handle, CURLOPT_WRITEDATA, buffer) == CURLE_OK, "userdata configuration failed");
}

// main executes easy, duplicated, partial and multi-handle content transfers.
int main(void) {
    const char *base = getenv("HTTP_SDK_BASE_URL");
    check(base != NULL, "loopback server URL is required");
    check(curl_global_init(CURL_GLOBAL_DEFAULT) == CURLE_OK, "curl global initialization failed");
    check(strcmp(curl_version_info(CURLVERSION_NOW)->version, LIBCURL_VERSION) == 0, "linked curl version mismatch");
    char url[256];
    check(snprintf(url, sizeof(url), "%s/fixed", base) < (int)sizeof(url), "URL exceeds fixture buffer");
    char secondUrl[256];
    check(snprintf(secondUrl, sizeof(secondUrl), "%s/second", base) < (int)sizeof(secondUrl), "second URL exceeds fixture buffer");
    easy_userdata(url);
    easy_uninitialized(url);
    easy_struct(url);
    easy_choice(url, 1);
    easy_choice(url, 0);
    easy_reuse(url, secondUrl);
    easy_separate(url, secondUrl);
    check(fixture_consumed == 16, "fixture did not perform all reads");
    struct Buffer original = {{0}, 0, 127};
    CURL *easy = curl_easy_init();
    configure(easy, &original, url);
    check(original.size == 0, "registration unexpectedly executed callback");
    check(curl_easy_perform(easy) == CURLE_OK, "easy execution failed");
    check(strcmp(original.data, "fixed-php-body") == 0, "easy content mismatch");
    long status = 0;
    check(curl_easy_getinfo(easy, CURLINFO_RESPONSE_CODE, &status) == CURLE_OK && status == 201, "status mismatch");
    struct Buffer duplicate = {{0}, 0, 127};
    CURL *copied = curl_easy_duphandle(easy);
    check(copied != NULL, "handle duplication failed");
    check(curl_easy_setopt(copied, CURLOPT_WRITEDATA, &duplicate) == CURLE_OK, "duplicate userdata configuration failed");
    check(curl_easy_perform(copied) == CURLE_OK, "duplicate execution failed");
    check(strcmp(duplicate.data, original.data) == 0 && original.size == 14, "userdata isolation mismatch");
    curl_easy_cleanup(copied);
    struct Buffer partial = {{0}, 0, 5};
    CURL *short_write = curl_easy_init();
    configure(short_write, &partial, url);
    check(curl_easy_perform(short_write) == CURLE_WRITE_ERROR, "short write did not fail");
    check(partial.size == 5 && strcmp(partial.data, "fixed") == 0, "written prefix mismatch");
    curl_easy_cleanup(short_write);
    struct Buffer multi_body = {{0}, 0, 127};
    CURL *multi_easy = curl_easy_init();
    configure(multi_easy, &multi_body, url);
    CURLM *multi = curl_multi_init();
    check(multi != NULL, "multi initialization failed");
    check(curl_multi_add_handle(multi, multi_easy) == CURLM_OK, "multi attachment failed");
    int running = 0;
    int descriptors = 0;
    do {
        check(curl_multi_perform(multi, &running) == CURLM_OK, "multi execution failed");
        if (running) check(curl_multi_poll(multi, NULL, 0, 1000, &descriptors) == CURLM_OK, "multi polling failed");
    } while (running);
    int remaining = 0;
    CURLMsg *completion = curl_multi_info_read(multi, &remaining);
    check(completion != NULL && completion->msg == CURLMSG_DONE && completion->data.result == CURLE_OK, "multi completion mismatch");
    check(strcmp(multi_body.data, "fixed-php-body") == 0, "multi content mismatch");
    check(curl_multi_remove_handle(multi, multi_easy) == CURLM_OK, "multi removal failed");
    check(curl_multi_cleanup(multi) == CURLM_OK, "multi cleanup failed");
    curl_easy_cleanup(multi_easy);
    curl_easy_cleanup(easy);
    curl_global_cleanup();
    printf("libcurl=%s; checks=%d; status=passed\n", LIBCURL_VERSION, checks);
    return 0;
}
