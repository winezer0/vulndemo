class Bus { void First() { KafkaProducer.Publish("TOPIC"); KafkaProducer.Publish("TOPIC"); } void Second() { KafkaProducer.Publish("TOPIC"); } }
// KafkaProducer.Publish("COMMENT")
