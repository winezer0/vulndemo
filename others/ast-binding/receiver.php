<?php
class ReceiverPHP {
    function receiverTarget() {
        receiverSink($this);
    }
    static function receiverCaller(ReceiverPHP $input) {
        $value = $input;
        $value->receiverTarget();
    }
}
