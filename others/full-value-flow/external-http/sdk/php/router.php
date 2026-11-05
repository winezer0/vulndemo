<?php
http_response_code($_SERVER['REQUEST_URI'] === '/error' ? 500 : 201);
header('Content-Type: text/plain');
echo $_SERVER['REQUEST_URI'] === '/second' ? 'second-php-body' : 'fixed-php-body';
