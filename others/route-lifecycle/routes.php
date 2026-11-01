<?php
use Illuminate\Support\Facades\Route;

Route::middleware('auth')->group(function () {
    Route::get('/php', 'Users@index')->middleware('audit');
});
Route::get('/php-open', 'Users@index');
