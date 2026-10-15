<?php

class A { public static function ping($value) {} }
class B extends A {
    public static function ping($value) {}
    public function run($value) {
        self::ping($value);
        parent::ping($value);
        static::ping($value);
    }
}
