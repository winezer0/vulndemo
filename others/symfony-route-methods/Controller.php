<?php
use Symfony\Component\Routing\Annotation\Route as WebRoute;
use Other\Routing\Route as OtherRoute;
class Controller {
    #[Route('/single', methods: ['GET'])]
    public function single() {}

    #[Route('/multi/{id}', methods: ['GET', 'POST'])]
    public function multi() {}

    #[Route('/dynamic', methods: [METHOD])]
    public function dynamic() {}

    #[Route('/interpolated', methods: ["G{$verb}"])]
    public function interpolated() {}

    #[WebRoute('/aliased')]
    public function aliased() {}

    #[OtherRoute('/unrelated')]
    public function unrelated() {}
}
