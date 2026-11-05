import httpx


async def streamed(asyncInput, asyncOtherInput):
    async with httpx.AsyncClient() as client:
        async with client.stream("GET", asyncInput) as response:
            asyncOut = await response.aread()
        async with client.stream("GET", asyncOtherInput) as other:
            asyncOtherOut = await other.aread()
    return asyncOut, asyncOtherOut


async def hooked(asyncHookInput, asyncHooks):
    async with httpx.AsyncClient(event_hooks=asyncHooks) as client:
        response = await client.get(asyncHookInput)
        asyncHookOut = response.content
    return asyncHookOut
