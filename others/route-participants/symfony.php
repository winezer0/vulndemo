<?php
use Symfony\Component\Routing\Annotation\Route;
class SymfonyRoutes {
    #[Route('/symfony', methods: ['GET'])]
    public function serve() { return 'ok'; }
}
