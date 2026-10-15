class OverloadBox {
public:
    int convert(double value) { return 1; }
    int convert(int value) { return value; }
};

int runAmbiguous(OverloadBox box, int value) {
    return box.convert(value);
}

class UniqueArityBox {
public:
    int convert(int value) { return value; }
    int convert(int left, int right) { return left + right; }
};

int runUnique(UniqueArityBox box, int value) {
    int output = box.convert(value);
    return output;
}
