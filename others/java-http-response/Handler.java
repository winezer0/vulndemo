import org.springframework.http.ResponseEntity;

class Worker { Object status() { return null; } }
class Handler {
    Object run(Worker worker) {
        Object ordinary = worker.status();
        return ResponseEntity.ok(ordinary);
    }
    Object shadow(Worker ResponseEntity, Object value) { return ResponseEntity.ok(value); }
}
