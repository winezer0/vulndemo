package main

func first(nats interface{ Publish(string) }, kafka interface{ WriteMessages(string) }) { nats.Publish("TOPIC"); kafka.WriteMessages("TOPIC") }; func second(nats interface{ Publish(string) }) { nats.Publish("TOPIC") }
func dynamic(nats interface{ Publish(string, string) }, topic string) { nats.Publish(topic, "PAYLOAD") }
