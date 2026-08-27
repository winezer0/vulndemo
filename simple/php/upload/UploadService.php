<?php

require_once __DIR__ . '/FileUploader.php';

final class UploadService
{
    private FileUploader $uploader;

    public function __construct(FileUploader $uploader)
    {
        $this->uploader = $uploader;
    }

    public function upload(array $file): string
    {
        return $this->uploader->upload($file);
    }
}
