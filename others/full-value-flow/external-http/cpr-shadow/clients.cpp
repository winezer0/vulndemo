#include <cpr/cpr.h>
#include <string>

namespace cpr {
// Get is a source overload and must not acquire the external HTTP model.
Response Get(const std::string &value) {
    Response result;
    result.text = value;
    return result;
}
}

std::string source_body(const std::string &shadowInput) {
    cpr::Response response = cpr::Get(shadowInput);
    std::string shadowOut = response.text;
    return shadowOut;
}
