async fn async_identity(value: String) -> String { value }
async fn async_middle(value: String) -> String { async_identity(value).await }
// async_task awaits the actual saved Future instance.
async fn async_task(async_taskInput: String) -> String { let pending = async_identity(async_taskInput); let async_taskOut = pending.await; async_taskOut }
// async_task_isolation consumes a different Future from the tainted producer.
async fn async_task_isolation(async_task_isolationInput: String) -> String { let first = async_identity(async_task_isolationInput); let second = async_identity(String::new()); let _ = first.await; let async_task_isolationOut = second.await; async_task_isolationOut }
// async_task_overwrite drops the old Future before replacing it.
async fn async_task_overwrite(async_task_overwriteInput: String) -> String { let mut pending = async_identity(async_task_overwriteInput); pending = async_identity(String::new()); let async_task_overwriteOut = pending.await; async_task_overwriteOut }
async fn async_await(async_awaitInput: String) -> String { let async_awaitOut = async_identity(async_awaitInput).await; async_awaitOut }
async fn async_isolation(async_isolationInput: String) -> String { let _ = async_identity(async_isolationInput).await; let async_isolationOut = async_identity(String::new()).await; async_isolationOut }
async fn async_wrapper(async_wrapperInput: String) -> String { let async_wrapperOut = async_middle(async_wrapperInput).await; async_wrapperOut }
async fn async_unused(async_unusedInput: String) -> String { let _ = async_identity(async_unusedInput).await; let async_unusedOut = String::new(); async_unusedOut }
struct AsyncBox { value: String }
// async_stored preserves an awaited result through an actual field write.
async fn async_stored(async_storedInput: String) -> String { let mut holder = AsyncBox { value: String::new() }; holder.value = async_identity(async_storedInput).await; let async_storedOut = holder.value; async_storedOut }
// async_stored_isolation keeps different awaited invocation results separate.
async fn async_stored_isolation(async_stored_isolationInput: String) -> String { let _ = async_identity(async_stored_isolationInput).await; let mut holder = AsyncBox { value: String::new() }; holder.value = async_identity(String::new()).await; let async_stored_isolationOut = holder.value; async_stored_isolationOut }
