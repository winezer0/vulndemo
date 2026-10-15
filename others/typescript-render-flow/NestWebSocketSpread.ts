import { WebSocketGateway, SubscribeMessage } from '@nestjs/websockets';

declare const options: {namespace: string};

@WebSocketGateway({namespace: '/wrong', ...options})
export class UnknownGateway {
  @SubscribeMessage('event') onEvent() {}
}

@WebSocketGateway({...options, namespace: '/known'})
export class KnownGateway {
  @SubscribeMessage('event') onEvent() {}
}
