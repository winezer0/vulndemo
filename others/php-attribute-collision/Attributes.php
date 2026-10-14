<?php

class Listener {
    #[AsEventListener(label: 'event: Fake')]
    public function onEvent() {}

    #[AsMessageHandler(label: 'handles: Fake')]
    public function onMessage() {}
}

#[Entity]
#[Table(label: 'name: forged')]
class User {}
