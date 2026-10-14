<?php

class Contract {}
class Impl {}
class Provider {
    public function register() { $this->app->instance(Contract::class, new Impl()); }
}
