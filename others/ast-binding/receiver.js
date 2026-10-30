class ReceiverService {
  run() { consume(this); }
  static create() { consume(this); }
}
const value = new ReceiverService();
value.run();
