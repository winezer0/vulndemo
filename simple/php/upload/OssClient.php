<?php

interface OssClient
{
    /**
     * Upload a local file to object storage.
     */
    public function putObject(string $bucket, string $key, string $localPath): void;
}
