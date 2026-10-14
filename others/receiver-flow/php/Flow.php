<?php
class A { public $x; public function ping() {} public function sink($x) {} }
class B { public $x; public function ping() {} public function sink($x) {} }

function outer() {
    $p = new A();
    $fn = function() use ($p) {
        $p->x = 1;
        $p->ping();
        $p = new B();
        $p->x = 2;
    };
    $p->x = 3;
}

function arrowOuter() {
    $p = new A();
    $first = fn() => $p->x;
    $p = new B();
    $p->x = 4;
    $nested = fn() => fn() => $p->x;
}

function branch($flag) {
    $p = new A();
    if ($flag) { $p = new B(); $p->sink(1); }
    else { $p->sink(2); }
    $p->sink(3);
}

function switches($v) {
    $p = new A();
    switch ($v) {
    case 1: $p = new B(); $p->sink(1); break;
    case 2: $p->sink(2); break;
    default: $p->sink(3);
    }
    $p->sink(4);
}

function matching($v) {
    $p = new A();
    match ($v) {
        1 => ($p = new B()),
        default => $p->sink(5),
    };
    $q = $p->sink(6);
}

function matchCondition($v) {
    $p = new A();
    match ($v) {
        ($v === 1 && ($p = new B())) => 0,
        default => $p->sink(7),
    };
    $p->sink(8);
}

function matchDefaultFirst($v) {
    $p = new A();
    match ($v) {
        default => $p->sink(9),
        ($v === 1 && ($p = new B())) => 0,
    };
    $p->sink(10);
}

function switchLabelEffect($v, $flag) {
    $p = new A();
    switch ($v) {
    case ($flag && ($p = new B())): break;
    case 2: $p->sink(11); break;
    default: $p->sink(12);
    }
    $p->sink(13);
}

function switchDefaultFirst($v, $flag) {
    $p = new A();
    switch ($v) {
    default: $p->sink(14); break;
    case ($flag && ($p = new B())): break;
    }
    $p->sink(15);
}

function switchReturnExit($v) {
    $p = new A();
    switch ($v) {
    case 1: $p = new B(); return;
    case 2: $p->sink(16); break;
    default: $p->sink(17);
    }
    $p->sink(18);
}

function switchThrowExit($v) {
    $p = new A();
    switch ($v) {
    case 1: $p = new B(); throw new Exception();
    case 2: $p->sink(19); break;
    default: $p->sink(20);
    }
    $p->sink(21);
}
