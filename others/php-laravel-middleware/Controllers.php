<?php
class AuthMiddleware {
    public function handle() {}
}

class StaticController {
    public function __construct() {
        $this->middleware(AuthMiddleware::class)->only(['index']);
    }
    public function index() {}
    public function show() {}
}

class DynamicController {
    public function __construct($actions) {
        $this->middleware(AuthMiddleware::class)->only($actions);
    }
    public function index() {}
    public function show() {}
}
