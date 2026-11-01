<?php
use Illuminate\Support\Facades\Route;

Route::get('/php', [Routes::class, 'serve']);
class Routes {
    public function serve() { return 'ok'; }
}
