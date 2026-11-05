#include <curl/curl.h>

extern void consume(const char *value);

static size_t userdata_write(char *data, size_t size, size_t count, void *user) {
    char *buffer = static_cast<char *>(user);
    if (size * count > 0) buffer[0] = data[0];
    return size * count;
}

void easy_userdata(const char *easyUserInput) {
    char buffer[64] = {0};
    char other[64] = {0};
    char *alias = buffer;
    const char *easyUserBefore = buffer;
    const char *easyUserOther = other;
    consume(easyUserBefore);
    CURL *handle = curl_easy_init();
    curl_easy_setopt(handle, CURLOPT_URL, easyUserInput);
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, userdata_write);
    curl_easy_setopt(handle, CURLOPT_WRITEDATA, &*alias);
    curl_easy_perform(handle);
    const char *easyUserOut = buffer;
    consume(easyUserOut);
    consume(easyUserOther);
    curl_easy_cleanup(handle);
}

void easy_uninitialized(const char *easyUninitInput) {
    char buffer[64];
    CURL *handle = curl_easy_init();
    curl_easy_setopt(handle, CURLOPT_URL, easyUninitInput);
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, userdata_write);
    curl_easy_setopt(handle, CURLOPT_WRITEDATA, buffer);
    curl_easy_perform(handle);
    const char *easyUninitOut = buffer;
    consume(easyUninitOut);
    curl_easy_cleanup(handle);
}

struct NativeResponse { char content[64]; char other[64]; };

static size_t userdata_struct_write(char *data, size_t size, size_t count, void *user) {
    auto *response = static_cast<NativeResponse *>(user);
    if (size * count > 0) response->content[0] = data[0];
    return size * count;
}

void easy_struct(const char *easyStructInput) {
    NativeResponse response = {};
    CURL *handle = curl_easy_init();
    curl_easy_setopt(handle, CURLOPT_URL, easyStructInput);
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, userdata_struct_write);
    curl_easy_setopt(handle, CURLOPT_WRITEDATA, &response);
    curl_easy_perform(handle);
    const char *easyStructOut = response.content;
    const char *easyStructOther = response.other;
    consume(easyStructOut);
    consume(easyStructOther);
    curl_easy_cleanup(handle);
}

void easy_choice(const char *easyChoiceInput, int choose) {
    char first[64] = {0};
    char second[64] = {0};
    char other[64] = {0};
    char *alias = first;
    if (choose) alias = second;
    CURL *handle = curl_easy_init();
    curl_easy_setopt(handle, CURLOPT_URL, easyChoiceInput);
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, userdata_write);
    curl_easy_setopt(handle, CURLOPT_WRITEDATA, alias);
    curl_easy_perform(handle);
    const char *easyChoiceFirst = first;
    const char *easyChoiceSecond = second;
    const char *easyChoiceOther = other;
    consume(easyChoiceFirst);
    consume(easyChoiceSecond);
    consume(easyChoiceOther);
    curl_easy_cleanup(handle);
}

// easy_reuse changes the URL and callback destination on the same handle.
void easy_reuse(const char *easyReuseFirstInput, const char *easyReuseSecondInput) {
    char first[64] = {0};
    char second[64] = {0};
    CURL *handle = curl_easy_init();
    curl_easy_setopt(handle, CURLOPT_WRITEFUNCTION, userdata_write);
    curl_easy_setopt(handle, CURLOPT_URL, easyReuseFirstInput);
    curl_easy_setopt(handle, CURLOPT_WRITEDATA, first);
    curl_easy_perform(handle);
    const char *easyReuseFirstOut = first;
    consume(easyReuseFirstOut);
    curl_easy_setopt(handle, CURLOPT_URL, easyReuseSecondInput);
    curl_easy_setopt(handle, CURLOPT_WRITEDATA, second);
    curl_easy_perform(handle);
    const char *easyReuseSecondOut = second;
    consume(easyReuseSecondOut);
    curl_easy_cleanup(handle);
}

// easy_separate uses two independent handles and callback destinations.
void easy_separate(const char *easySeparateFirstInput, const char *easySeparateSecondInput) {
    char first[64] = {0};
    char second[64] = {0};
    CURL *firstHandle = curl_easy_init();
    CURL *secondHandle = curl_easy_init();
    curl_easy_setopt(firstHandle, CURLOPT_URL, easySeparateFirstInput);
    curl_easy_setopt(firstHandle, CURLOPT_WRITEFUNCTION, userdata_write);
    curl_easy_setopt(firstHandle, CURLOPT_WRITEDATA, first);
    curl_easy_setopt(secondHandle, CURLOPT_URL, easySeparateSecondInput);
    curl_easy_setopt(secondHandle, CURLOPT_WRITEFUNCTION, userdata_write);
    curl_easy_setopt(secondHandle, CURLOPT_WRITEDATA, second);
    curl_easy_perform(firstHandle);
    curl_easy_perform(secondHandle);
    const char *easySeparateFirstOut = first;
    const char *easySeparateSecondOut = second;
    consume(easySeparateFirstOut);
    consume(easySeparateSecondOut);
    curl_easy_cleanup(firstHandle);
    curl_easy_cleanup(secondHandle);
}
