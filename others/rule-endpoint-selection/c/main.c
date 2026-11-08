#include <unistd.h>

int main(void) {
    char *args[] = {"echo", 0};
    execve("/bin/echo", args, 0);
    return 0;
}
