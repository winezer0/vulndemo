#if ENABLED
int maybe(int input) { return input; }
#else
int maybe(int input) { return input + 1; }
#endif
int uncertain(int input) { return maybe(input); }
