#include <cpr/cpr.h>
#include <memory>
#include <string>

// cpr_configured preserves the unknown variadic options overload as a candidate.
std::string cpr_configured(const std::string &cprOptionInput, const cpr::Parameters &options) {
    cpr::Url url{cprOptionInput};
    cpr::Response response = cpr::Get(url, options);
    std::string cprOptionOut = response.text;
    return cprOptionOut;
}

// cpr_independent keeps the later response separate from another actual request.
std::string cpr_independent(const std::string &cprIsolatedInput) {
    cpr::Url changedUrl{cprIsolatedInput};
    cpr::Response changed = cpr::Get(changedUrl);
    cpr::Url cleanUrl{"https://example.invalid/fixed"};
    cpr::Response clean = cpr::Get(cleanUrl);
    std::string cprIsolatedOut = clean.text;
    return cprIsolatedOut;
}

// cpr_session reads the response produced by an explicitly configured session.
std::string cpr_session(const std::string &cprSessionInput) {
    cpr::Session session;
    session.SetUrl(cpr::Url{cprSessionInput});
    cpr::Response response = session.Get();
    std::string cprSessionOut = response.text;
    return cprSessionOut;
}

// cpr_session_async consumes a configured session's deferred response.
std::string cpr_session_async(const std::string &cprAsyncInput) {
    auto session = std::make_shared<cpr::Session>();
    session->SetUrl(cpr::Url{cprAsyncInput});
    cpr::AsyncResponse future = session->GetAsync();
    cpr::Response response = future.get();
    std::string cprAsyncOut = response.text;
    return cprAsyncOut;
}
