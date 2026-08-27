<?php

require_once __DIR__ . '/BaseController.php';
require_once __DIR__ . '/../upload/UploadService.php';

final class UploadController extends BaseController
{
    private UploadService $service;

    public function __construct(UploadService $service)
    {
        $this->service = $service;
    }

    public function upload(array $files, string $field = 'file'): void
    {
        try {
            $location = $this->service->upload($files[$field] ?? []);
            $this->json(['success' => true, 'location' => $location]);
        } catch (Throwable $error) {
            $this->json(['success' => false, 'message' => $error->getMessage()], 400);
        }
    }
}
