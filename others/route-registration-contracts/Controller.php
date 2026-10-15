<?php
use Symfony\Component\Routing\Attribute\Route;
class Controller {
    #[Route('/first', methods: ['GET', 'POST'])]
    #[Route('/second')]
    #[Route(path: Paths::DYNAMIC)]
    public function items() {}
}
