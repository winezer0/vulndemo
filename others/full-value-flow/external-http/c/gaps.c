extern void update_unknown(int *output, int value);

void unknown_native(int **target, int unknownNativeInput) {
    int unknownNativeIndirectBefore = **target;
    int independent = 0;
    int unknownNativeIndirectOther = independent;
    update_unknown(&**target, unknownNativeInput);
    int unknownNativeOut = unknownNativeInput;
    int unknownNativeIndirectOut = **target;
    int unknownNativeIsolated = 0;
    (void)unknownNativeOut;
}

void update_offset(char *target, char input);
void offset_native(char offsetNativeInput, int offset) {
    char buffer[8] = {0};
    char other[8] = {0};
    char *alias = buffer;
    char *offsetNativeBefore = buffer;
    update_offset(alias + offset, offsetNativeInput);
    char *offsetNativeOut = buffer;
    char *offsetNativeOther = other;
}

void indirect_mutate(int **target, int input) {
    update_unknown(&**target, input);
}

int indirect_caller(int indirectCrossInput) {
    int value = 0;
    int independent = 0;
    int *pointer = &value;
    int indirectCrossBefore = value;
    indirect_mutate(&pointer, indirectCrossInput);
    int indirectCrossOut = value;
    int indirectCrossOther = independent;
    return indirectCrossOut;
}
