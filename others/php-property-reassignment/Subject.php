<?php
class A { public function execute() {} }
class Subject {
    private $service;
    public function __construct($unknown) {
        $this->service = new A();
        $this->service = $unknown;
    }
    public function run() { $this->service->execute(); }
}
