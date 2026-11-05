#include <curl/curl.h>
#include <cpr/cpr.h>
#include <string>

extern void consume(const char *value);

static size_t easy_write(char *data, size_t size, size_t count, void *user) {
    const char *easyOut = data;
    consume(easyOut);
    (void)user;
    return size * count;
}

void easy_body(const char *easyInput) {
    CURL *handle = curl_easy_init();
    curl_easy_setopt(handle, CURLOPT_URL, easyInput);
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, easy_write);
    curl_easy_perform(handle);
    curl_easy_cleanup(handle);
}

static size_t multi_write(char *data, size_t size, size_t count, void *user) {
    const char *multiOut = data;
    consume(multiOut);
    (void)user;
    return size * count;
}

void multi_body(const char *multiInput) {
    CURL *handle = curl_easy_init();
    CURLM *multi = curl_multi_init();
    curl_easy_setopt(handle, CURLOPT_URL, multiInput);
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, multi_write);
    curl_multi_add_handle(multi, handle);
    int running = 0;
    do {
        curl_multi_perform(multi, &running);
        if (running) {
            curl_multi_poll(multi, nullptr, 0, 1000, nullptr);
        }
    } while (running);
    curl_multi_remove_handle(multi, handle);
    curl_easy_cleanup(handle);
    curl_multi_cleanup(multi);
}

std::string cpr_body(const std::string &cprInput) {
    cpr::Url url{cprInput};
    cpr::Response response = cpr::Get(url);
    std::string cprOut = response.text;
    return cprOut;
}
