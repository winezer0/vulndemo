class Messaging {
    void publish(Object payload) {
        kafkaTemplate.send("orders", payload); redisTemplate.publish("orders", payload);
    }
}
