<?php

require_once __DIR__ . '/AbstractFileUploader.php';

final class LocalFileUploader extends AbstractFileUploader
{
    private string $uploadDirectory;

    public function __construct(string $uploadDirectory)
    {
        $this->uploadDirectory = $uploadDirectory;
    }

    public function upload(array $file): string
    {
        $this->validate($file);

        if (!is_dir($this->uploadDirectory) && !mkdir($this->uploadDirectory, 0775, true)) {
            throw new RuntimeException('Unable to create local upload directory.');
        }

        $target = rtrim($this->uploadDirectory, DIRECTORY_SEPARATOR)
            . DIRECTORY_SEPARATOR . $this->originalName($file);
        if (!move_uploaded_file($this->temporaryPath($file), $target)) {
            throw new RuntimeException('Unable to move uploaded file.');
        }

        return $target;
    }
}
