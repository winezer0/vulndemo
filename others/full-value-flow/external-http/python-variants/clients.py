import httpx


class Holder:
    pass


def custom_client(httpxHookInput, hooks):
    client = httpx.Client(event_hooks=hooks)
    response = client.get(httpxHookInput)
    httpxHookOut = response.content
    return httpxHookOut


def stream_response(httpxStreamInput):
    with httpx.stream("GET", httpxStreamInput) as response:
        httpxStreamOut = response.read()
    return httpxStreamOut


def stream_after_context(httpxClosedInput):
    with httpx.stream("GET", httpxClosedInput, trust_env=False) as response:
        pass
    httpxClosedOut = response.read()
    return httpxClosedOut


def destructured(tupleInput):
    with make_context(tupleInput) as (first, second):
        tupleFirstOut = first
        tupleSecondOut = second
    return tupleFirstOut, tupleSecondOut


def indexed(indexInput, indexOld):
    box = {"item": indexOld}
    before = box.get("item")
    with make_context(indexInput) as box["item"]:
        indexOut = box["item"]
    return before, indexOut


def attributed(attributeInput):
    box = Holder()
    attributeBefore = box.data
    with make_context(attributeInput) as box.data:
        attributeOut = box.data
    return attributeBefore, attributeOut


def conditional(branchInput, branchOld, condition):
    value = branchOld
    if condition:
        with make_context(branchInput) as value:
            pass
    branchOut = value
    return branchOut
