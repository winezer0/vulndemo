<?php
use Symfony\Component\EventDispatcher\Attribute\AsEventListener as Listen;
use Symfony\Component\Messenger\Attribute\AsMessageHandler as Handle;
use Doctrine\ORM\Mapping\Entity as Model;
use Doctrine\ORM\Mapping\Table as TableAlias;
use App\Event\Created as EventAlias;
use App\Message\Created as MessageAlias;
use App\Observer\UserObserver as ObserverAlias;
class User {
    #[Listen(event: EventAlias::class)]
    public function onCreated() {}
    #[Handle(handles: MessageAlias::class)]
    public function onMessage() {}
}
#[Model]
#[TableAlias(name: 'actual_orders')]
#[ObservedBy(ObserverAlias::class)]
class Order {}
