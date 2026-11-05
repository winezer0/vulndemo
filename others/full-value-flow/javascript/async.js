async function async_identity(value) { return value; }
// async_schedule consumes only the payload of this exact standard Promise factory.
async function async_schedule(async_scheduleInput) { const pending = Promise.resolve(async_scheduleInput); const async_scheduleOut = await pending; return async_scheduleOut; }
// async_schedule_isolation keeps separate standard Promise instances isolated.
async function async_schedule_isolation(async_schedule_isolationInput) { const first = Promise.resolve(async_schedule_isolationInput); const second = Promise.resolve("safe"); await first; const async_schedule_isolationOut = await second; return async_schedule_isolationOut; }
// async_schedule_overwrite consumes only the replacement standard Promise.
async function async_schedule_overwrite(async_schedule_overwriteInput) { let pending = Promise.resolve(async_schedule_overwriteInput); pending = Promise.resolve("safe"); const async_schedule_overwriteOut = await pending; return async_schedule_overwriteOut; }
async function async_middle(value) { return await async_identity(value); }
// async_task awaits the actual Promise saved by this invocation.
async function async_task(async_taskInput) { const pending = async_identity(async_taskInput); const async_taskOut = await pending; return async_taskOut; }
// async_task_isolation consumes the safe Promise rather than another invocation's Promise.
async function async_task_isolation(async_task_isolationInput) { const first = async_identity(async_task_isolationInput); const second = async_identity("safe"); await first; const async_task_isolationOut = await second; return async_task_isolationOut; }
// async_task_overwrite replaces the pending Promise before consumption.
async function async_task_overwrite(async_task_overwriteInput) { let pending = async_identity(async_task_overwriteInput); pending = async_identity("safe"); const async_task_overwriteOut = await pending; return async_task_overwriteOut; }
async function async_await(async_awaitInput) { const async_awaitOut = await async_identity(async_awaitInput); return async_awaitOut; }
async function async_isolation(async_isolationInput) { await async_identity(async_isolationInput); const async_isolationOut = await async_identity("safe"); return async_isolationOut; }
async function async_wrapper(async_wrapperInput) { const async_wrapperOut = await async_middle(async_wrapperInput); return async_wrapperOut; }
async function async_unused(async_unusedInput) { await async_identity(async_unusedInput); const async_unusedOut = "safe"; return async_unusedOut; }
// async_stored preserves an awaited result through an actual property write.
async function async_stored(async_storedInput) { const holder = {}; holder.value = await async_identity(async_storedInput); const async_storedOut = holder.value; return async_storedOut; }
// async_stored_isolation discards an unrelated awaited result before storing a safe one.
async function async_stored_isolation(async_stored_isolationInput) { await async_identity(async_stored_isolationInput); const holder = {}; holder.value = await async_identity("safe"); const async_stored_isolationOut = holder.value; return async_stored_isolationOut; }
