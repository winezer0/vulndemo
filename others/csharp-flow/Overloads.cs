class Box {
    int Convert(int value) { return value; }
    string Convert(string value) { return value; }
}

class OverloadUse {
    int Run(Box box, int value) { return box.Convert(value); }
}

class ArityBox {
    int Convert(int value) { return value; }
    int Convert(int left, int right) { return left + right; }
}

class ArityUse {
    int RunUnique(ArityBox box, int value) { int output = box.Convert(value); return output; }
}
