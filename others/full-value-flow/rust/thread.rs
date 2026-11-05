use std::thread::{spawn, JoinHandle};

// async_callback consumes the payload returned by its own spawned closure.
fn async_callback(async_callbackInput: String) -> std::thread::Result<String> {
    let produce = move || async_callbackInput;
    let pending: JoinHandle<String> = spawn(produce);
    let async_callbackOut = pending.join();
    async_callbackOut
}
// async_callback_isolation consumes a different thread's result.
fn async_callback_isolation(async_callback_isolationInput: String) -> std::thread::Result<String> {
    let tainted = move || async_callback_isolationInput;
    let safe = || String::from("safe");
    let first: JoinHandle<String> = spawn(tainted);
    let second: JoinHandle<String> = spawn(safe);
    let _ = first.join();
    let async_callback_isolationOut = second.join();
    async_callback_isolationOut
}
