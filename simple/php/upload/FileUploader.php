<?php

interface FileUploader
{
    /**
     * Upload a normalized PHP file array and return its storage location.
     */
    public function upload(array $file): string;
}
