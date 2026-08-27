<?php

require_once __DIR__ . '/AbstractFileUploader.php';
require_once __DIR__ . '/OssClient.php';

final class OssFileUploader extends AbstractFileUploader
{
    private OssClient $client;
    private string $bucket;
    private string $prefix;

    public function __construct(OssClient $client, string $bucket, string $prefix = '')
    {
        $this->client = $client;
        $this->bucket = $bucket;
        $this->prefix = $prefix;
    }

    public function upload(array $file): string
    {
        $this->validate($file);

        $key = trim($this->prefix, '/') . '/' . $this->originalName($file);
        $key = ltrim($key, '/');
        $this->client->putObject($this->bucket, $key, $this->temporaryPath($file));

        return 'oss://' . $this->bucket . '/' . $key;
    }
}
