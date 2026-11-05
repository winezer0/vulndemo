suspend fun async_identity(value: String): String { return value }
suspend fun async_middle(value: String): String { return async_identity(value) }
suspend fun async_await(async_awaitInput: String): String { val async_awaitOut = async_identity(async_awaitInput); return async_awaitOut }
suspend fun async_isolation(async_isolationInput: String): String { async_identity(async_isolationInput); val async_isolationOut = async_identity("safe"); return async_isolationOut }
suspend fun async_wrapper(async_wrapperInput: String): String { val async_wrapperOut = async_middle(async_wrapperInput); return async_wrapperOut }
suspend fun async_unused(async_unusedInput: String): String { async_identity(async_unusedInput); val async_unusedOut = "safe"; return async_unusedOut }
class AsyncBox { var value: String = ""; }
// async_stored preserves a suspended result through an actual field write.
suspend fun async_stored(async_storedInput: String): String { val holder = AsyncBox(); holder.value = async_identity(async_storedInput); val async_storedOut = holder.value; return async_storedOut }
// async_stored_isolation keeps different suspended invocation results separate.
suspend fun async_stored_isolation(async_stored_isolationInput: String): String { async_identity(async_stored_isolationInput); val holder = AsyncBox(); holder.value = async_identity("safe"); val async_stored_isolationOut = holder.value; return async_stored_isolationOut }
