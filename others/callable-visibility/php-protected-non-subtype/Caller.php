<?php
namespace Demo;

class Caller {
    public function call(): void { (new Service())->run(); }
}
