<?php
class Routes { public function first() { \think\Route::get('/first', 'UserController@index'); } public function second() { \think\Route::get('/second', 'UserController@index'); } }
