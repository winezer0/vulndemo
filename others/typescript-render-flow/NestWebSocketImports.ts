import { WebSocketGateway as Gateway, SubscribeMessage as Message } from '@nestjs/websockets';
@Gateway('/chat')
export class ImportedGateway {
  @Message('joined')
  handle() {}
}
@Gateway('/chat')
export class SecondImportedGateway {
  @Message('joined')
  handleSecond() {}
}
function WebSocketGateway(_path: string): ClassDecorator { return () => {}; }
function SubscribeMessage(_topic: string): MethodDecorator { return () => {}; }
@WebSocketGateway('/fake')
export class LocalGateway {
  @SubscribeMessage('fake')
  handle() {}
}
