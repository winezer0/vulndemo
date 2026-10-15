function first() { kafka.publish("orders", payload); } function second() { kafka.publish("orders", payload); }
function other() { redis.publish("orders", payload); }
function delayed() { return () => kafka.subscribe("orders", handler); }
