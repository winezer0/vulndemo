async function async_identity(value: string): Promise<string> { return value; }
// async_schedule consumes the payload of this exact standard Promise factory.
async function async_schedule(async_scheduleInput: string): Promise<string> { const pending = Promise.resolve(async_scheduleInput); const async_scheduleOut = await pending; return async_scheduleOut; }
// async_schedule_isolation keeps separate standard Promise instances isolated.
async function async_schedule_isolation(async_schedule_isolationInput: string): Promise<string> { const first = Promise.resolve(async_schedule_isolationInput); const second = Promise.resolve("safe"); await first; const async_schedule_isolationOut = await second; return async_schedule_isolationOut; }
// async_schedule_overwrite consumes only the replacement standard Promise.
async function async_schedule_overwrite(async_schedule_overwriteInput: string): Promise<string> { let pending = Promise.resolve(async_schedule_overwriteInput); pending = Promise.resolve("safe"); const async_schedule_overwriteOut = await pending; return async_schedule_overwriteOut; }
async function async_middle(value: string): Promise<string> { return await async_identity(value); }
// async_task awaits the actual Promise saved by this invocation.
async function async_task(async_taskInput: string): Promise<string> { const pending = async_identity(async_taskInput); const async_taskOut = await pending; return async_taskOut; }
// async_task_isolation consumes a different Promise from the tainted producer.
async function async_task_isolation(async_task_isolationInput: string): Promise<string> { const first = async_identity(async_task_isolationInput); const second = async_identity("safe"); await first; const async_task_isolationOut = await second; return async_task_isolationOut; }
// async_task_overwrite replaces the pending Promise before consumption.
async function async_task_overwrite(async_task_overwriteInput: string): Promise<string> { let pending = async_identity(async_task_overwriteInput); pending = async_identity("safe"); const async_task_overwriteOut = await pending; return async_task_overwriteOut; }
async function async_await(async_awaitInput: string): Promise<string> { const async_awaitOut = await async_identity(async_awaitInput); return async_awaitOut; }
async function async_isolation(async_isolationInput: string): Promise<string> { await async_identity(async_isolationInput); const async_isolationOut = await async_identity("safe"); return async_isolationOut; }
async function async_wrapper(async_wrapperInput: string): Promise<string> { const async_wrapperOut = await async_middle(async_wrapperInput); return async_wrapperOut; }
async function async_unused(async_unusedInput: string): Promise<string> { await async_identity(async_unusedInput); const async_unusedOut = "safe"; return async_unusedOut; }
// async_stored preserves an awaited result through an actual property write.
async function async_stored(async_storedInput: string): Promise<string> { const holder = {value: ""}; holder.value = await async_identity(async_storedInput); const async_storedOut = holder.value; return async_storedOut; }
// async_stored_isolation keeps different awaited invocation results separate.
async function async_stored_isolation(async_stored_isolationInput: string): Promise<string> { await async_identity(async_stored_isolationInput); const holder = {value: ""}; holder.value = await async_identity("safe"); const async_stored_isolationOut = holder.value; return async_stored_isolationOut; }
