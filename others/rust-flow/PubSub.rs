fn send() { kafka.publish("A"); redis.publish("B"); kafka.publish("A"); }
// nats.publish("COMMENT");
fn other() { let name = "DYNAMIC"; kafka.publish(name); }
