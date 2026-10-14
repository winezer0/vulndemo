class ShadowA { public: int value; };
void readShadow(ShadowA item) {
    int before = item.value;
    { auto item = 42; int inner = item.value; }
    int after = item.value;
}
