#include "vuln_parent.h"
#include <cstdio>
#include <cstdlib>
#include <curl/curl.h>

// Windows兼容性：popen/pclose在MSVC中名为_popen/_pclose
#ifdef _WIN32
#define popen _popen
#define pclose _pclose
#endif

// 回调函数，用于libcurl接收数据
static size_t WriteCallback(void* contents, size_t size, size_t nmemb, std::string* userp) {
    userp->append((char*)contents, size * nmemb);
    return size * nmemb;
}

// 公共父类方法实现，集中存放所有漏洞核心方法

// 命令执行漏洞方法
// 漏洞点：直接将用户输入拼接到系统命令中执行
std::string VulnParent::executeCommand(const std::string& cmd) {
    std::string full_cmd = "echo " + cmd;
    char buffer[256];
    std::string result;

    // 漏洞点：popen直接执行用户可控的cmd参数，存在命令注入
    FILE* pipe = popen(full_cmd.c_str(), "r");
    if (!pipe) {
        return "Error: Failed to execute command";
    }

    while (fgets(buffer, sizeof(buffer), pipe) != nullptr) {
        result += buffer;
    }
    pclose(pipe);
    return result;
}

// 代码执行漏洞方法
// 漏洞点：通过popen执行用户构造的代码字符串
std::string VulnParent::executeCode(const std::string& code) {
    // 漏洞点：将用户输入作为代码拼接到命令中执行（模拟代码执行）
    // Windows使用python，Linux使用python3
#ifdef _WIN32
    std::string cmd = "python -c \"" + code + "\"";
#else
    std::string cmd = "python3 -c \"" + code + "\"";
#endif

    char buffer[256];
    std::string result;

    FILE* pipe = popen(cmd.c_str(), "r");
    if (!pipe) {
        return "Error: Failed to execute code";
    }

    while (fgets(buffer, sizeof(buffer), pipe) != nullptr) {
        result += buffer;
    }
    pclose(pipe);
    return result;
}

// SSRF漏洞方法
// 漏洞点：直接请求用户指定的URL
std::string VulnParent::fetchUrl(const std::string& url) {
    CURL* curl = curl_easy_init();
    std::string response;

    if (curl) {
        // 漏洞点：直接使用用户提供的URL发起请求，存在SSRF
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
        curl_easy_setopt(curl, CURLOPT_TIMEOUT, 10L);

        CURLcode res = curl_easy_perform(curl);
        if (res != CURLE_OK) {
            response = "Error: " + std::string(curl_easy_strerror(res));
        }

        curl_easy_cleanup(curl);
    }

    return response;
}
