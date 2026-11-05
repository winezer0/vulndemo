using System.Threading.Tasks;

class AsyncFlow {
    // async_schedule consumes the payload of this exact standard Task factory.
    public static async Task<string> async_schedule(string async_scheduleInput) { var pending = Task.FromResult(async_scheduleInput); var async_scheduleOut = await pending; return async_scheduleOut; }
    // async_schedule_isolation keeps independent Task instances isolated.
    public static async Task<string> async_schedule_isolation(string async_schedule_isolationInput) { var first = Task.FromResult(async_schedule_isolationInput); var second = Task.FromResult("safe"); await first; var async_schedule_isolationOut = await second; return async_schedule_isolationOut; }
    // async_schedule_overwrite consumes only the replacement Task.
    public static async Task<string> async_schedule_overwrite(string async_schedule_overwriteInput) { var pending = Task.FromResult(async_schedule_overwriteInput); pending = Task.FromResult("safe"); var async_schedule_overwriteOut = await pending; return async_schedule_overwriteOut; }
    class AsyncBox { public string Value = ""; }
    public static async System.Threading.Tasks.Task<string> async_identity(string value) { return value; }
    public static async System.Threading.Tasks.Task<string> async_middle(string value) { return await async_identity(value); }
    // async_task awaits the actual saved Task instance.
    public static async System.Threading.Tasks.Task<string> async_task(string async_taskInput) { var pending = async_identity(async_taskInput); var async_taskOut = await pending; return async_taskOut; }
    // async_task_isolation consumes a different Task from the tainted producer.
    public static async System.Threading.Tasks.Task<string> async_task_isolation(string async_task_isolationInput) { var first = async_identity(async_task_isolationInput); var second = async_identity("safe"); await first; var async_task_isolationOut = await second; return async_task_isolationOut; }
    // async_task_overwrite consumes only the replacement Task.
    public static async System.Threading.Tasks.Task<string> async_task_overwrite(string async_task_overwriteInput) { var pending = async_identity(async_task_overwriteInput); pending = async_identity("safe"); var async_task_overwriteOut = await pending; return async_task_overwriteOut; }
    public static async System.Threading.Tasks.Task<string> async_await(string async_awaitInput) { var async_awaitOut = await async_identity(async_awaitInput); return async_awaitOut; }
    public static async System.Threading.Tasks.Task<string> async_isolation(string async_isolationInput) { await async_identity(async_isolationInput); var async_isolationOut = await async_identity("safe"); return async_isolationOut; }
    public static async System.Threading.Tasks.Task<string> async_wrapper(string async_wrapperInput) { var async_wrapperOut = await async_middle(async_wrapperInput); return async_wrapperOut; }
    public static async System.Threading.Tasks.Task<string> async_unused(string async_unusedInput) { await async_identity(async_unusedInput); var async_unusedOut = "safe"; return async_unusedOut; }
    // async_stored preserves an awaited result through an actual field write.
    public static async System.Threading.Tasks.Task<string> async_stored(string async_storedInput) { var holder = new AsyncBox(); holder.Value = await async_identity(async_storedInput); var async_storedOut = holder.Value; return async_storedOut; }
    // async_stored_isolation keeps different awaited invocation results separate.
    public static async System.Threading.Tasks.Task<string> async_stored_isolation(string async_stored_isolationInput) { await async_identity(async_stored_isolationInput); var holder = new AsyncBox(); holder.Value = await async_identity("safe"); var async_stored_isolationOut = holder.Value; return async_stored_isolationOut; }
}
