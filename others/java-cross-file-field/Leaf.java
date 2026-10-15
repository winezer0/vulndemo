package demo;

class Leaf extends Middle {
    void sink(int value) { }
    void run() { this.inherited++; sink(this.inherited); }
}
