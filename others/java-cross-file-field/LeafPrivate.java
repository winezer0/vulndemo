package demo;

class LeafPrivate extends MiddlePrivate {
    void sink(int value) { }
    void run() { this.inherited++; sink(this.inherited); }
}
