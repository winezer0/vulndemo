package consumer;

import base.Base;

class Sub extends Base {
    void run() {
        this.inherited++;
        this.exposed++;
        this.packageOnly++;
        this.hidden++;
        sink(this.inherited);
    }

    void sink(int value) {}
}
