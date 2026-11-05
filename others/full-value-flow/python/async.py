import asyncio

# async_schedule retains the actual coroutine payload through its own scheduled task.
async def async_schedule(async_scheduleInput):
    coroutine = async_identity(async_scheduleInput)
    pending = asyncio.create_task(coroutine)
    async_scheduleOut = await pending
    return async_scheduleOut

# async_schedule_isolation consumes a different task from the tainted producer.
async def async_schedule_isolation(async_schedule_isolationInput):
    coroutine = async_identity(async_schedule_isolationInput)
    safe_coroutine = async_identity("safe")
    first = asyncio.create_task(coroutine)
    second = asyncio.create_task(safe_coroutine)
    await first
    async_schedule_isolationOut = await second
    return async_schedule_isolationOut

# async_schedule_overwrite retains only the replacement task value.
async def async_schedule_overwrite(async_schedule_overwriteInput):
    coroutine = async_identity(async_schedule_overwriteInput)
    safe_coroutine = async_identity("safe")
    pending = asyncio.ensure_future(coroutine)
    await pending
    pending = asyncio.ensure_future(safe_coroutine)
    async_schedule_overwriteOut = await pending
    return async_schedule_overwriteOut

async def async_identity(value):
    return value

# async_task consumes the actual saved coroutine instance.
async def async_task(async_taskInput):
    pending = async_identity(async_taskInput)
    async_taskOut = await pending
    return async_taskOut

# async_task_isolation keeps independent coroutine instances separate.
async def async_task_isolation(async_task_isolationInput):
    first = async_identity(async_task_isolationInput)
    second = async_identity("safe")
    await first
    async_task_isolationOut = await second
    return async_task_isolationOut

# async_task_overwrite consumes only the replacement coroutine.
async def async_task_overwrite(async_task_overwriteInput):
    pending = async_identity(async_task_overwriteInput)
    pending.close()
    pending = async_identity("safe")
    async_task_overwriteOut = await pending
    return async_task_overwriteOut

async def async_middle(value):
    return await async_identity(value)

async def async_await(async_awaitInput):
    async_awaitOut = await async_identity(async_awaitInput)
    return async_awaitOut

async def async_isolation(async_isolationInput):
    await async_identity(async_isolationInput)
    async_isolationOut = await async_identity("safe")
    return async_isolationOut

async def async_wrapper(async_wrapperInput):
    async_wrapperOut = await async_middle(async_wrapperInput)
    return async_wrapperOut

async def async_unused(async_unusedInput):
    await async_identity(async_unusedInput)
    async_unusedOut = "safe"
    return async_unusedOut

class AsyncBox:
    pass

# async_stored preserves an awaited result through an actual attribute write.
async def async_stored(async_storedInput):
    holder = AsyncBox()
    holder.value = await async_identity(async_storedInput)
    async_storedOut = holder.value
    return async_storedOut

# async_stored_isolation keeps different awaited invocation results separate.
async def async_stored_isolation(async_stored_isolationInput):
    await async_identity(async_stored_isolationInput)
    holder = AsyncBox()
    holder.value = await async_identity("safe")
    async_stored_isolationOut = holder.value
    return async_stored_isolationOut
