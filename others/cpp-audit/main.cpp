#include <string>

void system(std::string cmd) {
    (void)cmd;
}

void run(std::string cmd) {
    system(cmd);
}

int main() {
    run("calc");
    return 0;
}
