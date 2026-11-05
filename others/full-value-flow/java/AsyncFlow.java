import java.util.concurrent.CompletableFuture;

class AsyncFlow {
    // async_schedule produces and consumes one actual standard Future instance.
    static String async_schedule(String async_scheduleInput) {
        CompletableFuture<String> pending = CompletableFuture.completedFuture(async_scheduleInput);
        String async_scheduleOut = pending.join();
        return async_scheduleOut;
    }

    // async_schedule_isolation keeps the safe Future separate from the tainted producer.
    static String async_schedule_isolation(String async_schedule_isolationInput) {
        CompletableFuture<String> first = CompletableFuture.completedFuture(async_schedule_isolationInput);
        CompletableFuture<String> second = CompletableFuture.completedFuture("safe");
        first.join();
        String async_schedule_isolationOut = second.join();
        return async_schedule_isolationOut;
    }

    // async_schedule_overwrite consumes only the replacement Future.
    static String async_schedule_overwrite(String async_schedule_overwriteInput) {
        CompletableFuture<String> pending = CompletableFuture.completedFuture(async_schedule_overwriteInput);
        pending = CompletableFuture.completedFuture("safe");
        String async_schedule_overwriteOut = pending.join();
        return async_schedule_overwriteOut;
    }
}
