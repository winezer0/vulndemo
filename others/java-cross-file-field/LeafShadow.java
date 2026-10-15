package demo;

class LeafShadow extends MiddleShadow {
    void sink(int value) { }
    void run() { this.inherited++; sink(this.inherited); }
}
