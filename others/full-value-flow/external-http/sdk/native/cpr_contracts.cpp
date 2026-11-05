#include <cstdlib>
#include <iostream>
#include <stdexcept>
#include <string>
#include <cpr/cpr.h>

std::string cpr_session(const std::string &url);
std::string cpr_session_async(const std::string &url);

// check rejects mismatched CPR behavior without external test dependencies.
static void check(bool condition, const char *message) {
    if (!condition) throw std::runtime_error(message);
}

// main checks actual response fields, session execution and HTTP error slots.
int main() {
    const char *base = std::getenv("HTTP_SDK_BASE_URL");
    check(base != nullptr, "loopback server URL is required");
    auto response = cpr::Get(cpr::Url{std::string(base) + "/fixed"}, cpr::Timeout{5000});
    check(response.error.code == cpr::ErrorCode::OK, "transport failure");
    check(response.status_code == 201, "status mismatch");
    check(response.text == "fixed-php-body", "body mismatch");
    cpr::Session session;
    session.SetUrl(cpr::Url{std::string(base) + "/error"});
    session.SetTimeout(cpr::Timeout{5000});
    auto failure = session.Get();
    check(failure.error.code == cpr::ErrorCode::OK && failure.status_code == 500, "HTTP and transport failure were confused");
    check(failure.text == "fixed-php-body", "HTTP failure body mismatch");
    check(response.status_code == 201, "response object isolation mismatch");
    session.SetUrl(cpr::Url{std::string(base) + "/second"});
    auto second = session.Get();
    check(second.text == "second-php-body", "session reuse body mismatch");
    check(failure.text == "fixed-php-body", "prior session result changed");
    check(cpr_session(std::string(base) + "/second") == "second-php-body", "session fixture body mismatch");
    check(cpr_session_async(std::string(base) + "/second") == "second-php-body", "async session fixture body mismatch");
    bool unownedRejected = false;
    try {
        cpr::Session unowned;
        auto unused = unowned.GetAsync();
    } catch (const std::bad_weak_ptr &) {
        unownedRejected = true;
    }
    check(unownedRejected, "async session must have shared ownership");
    std::cout << "cpr=1.11.1; checks=11; status=passed\n";
}
