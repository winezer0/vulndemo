<?php

$topCurlInput = source_value();
$topCurlHandle = curl_init($topCurlInput);
curl_setopt($topCurlHandle, CURLOPT_RETURNTRANSFER, true);
$topCurlOut = curl_exec($topCurlHandle);
