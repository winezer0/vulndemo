<?php

class A { public function ping() {} }
class B { public function ping() {} }

class Holder {
    private $service;

    public function __construct($flag) {
        if ($flag) { $this->service = new A(); }
    }

    public function run() { $this->service->ping(); }
}
