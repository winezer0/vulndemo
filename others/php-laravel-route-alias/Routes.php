<?php
use Illuminate\Support\Facades\Route as WebRoute;
use App\Http\Controllers\UserController;
use Other\Facades\Route as OtherRoute;

class OtherController { public function index() {} }
WebRoute::get('/users', [UserController::class, 'index']);
OtherRoute::post('/not-laravel', 'UserController@store');
