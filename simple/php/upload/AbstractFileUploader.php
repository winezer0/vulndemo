<?php

require_once __DIR__ . '/FileUploader.php';

abstract class AbstractFileUploader implements FileUploader
{
    protected function validate(array $file): void
    {
        if (($file['error'] ?? UPLOAD_ERR_NO_FILE) !== UPLOAD_ERR_OK) {
            throw new RuntimeException('Upload failed.');
        }

        if (!isset($file['tmp_name']) || !is_string($file['tmp_name'])) {
            throw new InvalidArgumentException('Temporary upload path is required.');
        }
    }

    protected function originalName(array $file): string
    {
        $name = basename((string) ($file['name'] ?? 'upload.bin'));
        return $name !== '' ? $name : 'upload.bin';
    }

    protected function temporaryPath(array $file): string
    {
        return (string) $file['tmp_name'];
    }
}
