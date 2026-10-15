<?php
\think\facade\Route::rule('items', 'ItemController@index', 'GET|POST');
\think\facade\Route::rule('dynamic', 'ItemController@index', $method);
\think\facade\Route::rule('invalid', 'ItemController@index', 'GET|NONSENSE');
