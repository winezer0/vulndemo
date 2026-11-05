import requests
import httpx


def requests_json(jsonInput, key):
    response = requests.get(jsonInput)
    decodedRequest = response.json()
    jsonOut = decodedRequest[key]
    return jsonOut


def httpx_json(httpxJsonInput, key):
    response = httpx.get(httpxJsonInput)
    decodedHttpx = response.json()
    httpxJsonOut = decodedHttpx[key]
    return httpxJsonOut


def configured_requests(hookInput, hooks):
    response = requests.get(hookInput, hooks=hooks)
    hookOut = response.content
    return hookOut
