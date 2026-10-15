import { SubscribeMessage, WebSocketGateway } from '@nestjs/websockets';
const namespace = '/dynamic';
const topic = 'dynamic';
@WebSocketGateway({ namespace })
export class DynamicGateway {
  @SubscribeMessage('known')
  handleKnown() {}
}
@WebSocketGateway({ namespace: '/chat' })
export class ChatGateway {
  @SubscribeMessage(topic)
  handleDynamic() {}
  @SubscribeMessage('saved')
  handleSaved() {}
}
