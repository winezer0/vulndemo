import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

class AsyncCallback {
    // async_callback binds a real supplier return to its own future.
    static String async_callback(String async_callbackInput) {
        Supplier<String> produce = () -> async_callbackInput;
        CompletableFuture<String> pending = CompletableFuture.supplyAsync(produce);
        String async_callbackOut = pending.join();
        return async_callbackOut;
    }
    // async_callback_isolation consumes only the independent safe task.
    static String async_callback_isolation(String async_callback_isolationInput) {
        Supplier<String> tainted = () -> async_callback_isolationInput;
        Supplier<String> safe = () -> "safe";
        CompletableFuture<String> first = CompletableFuture.supplyAsync(tainted);
        CompletableFuture<String> second = CompletableFuture.supplyAsync(safe);
        String async_callback_isolationOut = second.join();
        return async_callback_isolationOut;
    }
}
