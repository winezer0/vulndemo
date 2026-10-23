struct choose { int value; };
int choose(int input) { return input; }
int known(int input) { return choose(input); }
int shadow(int (*choose)(int), int input) {
    goto choose;
choose:
    return choose(input);
}
typedef int count;
int local(int input) { int count = input; return count; }
