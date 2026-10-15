<?php

class ItemController {
    public function index() {}
}

Route::match(['GET', 'POST'], '/items', 'ItemController@index');
Route::match([$method], '/dynamic', 'ItemController@index');
