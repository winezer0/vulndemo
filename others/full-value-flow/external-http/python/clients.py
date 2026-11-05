import requests
import httpx
import urllib.request


def requests_body(requestsInput):
    response = requests.get(requestsInput)
    requestsOut = response.content
    return requestsOut


def httpx_body(httpxInput):
    response = httpx.get(httpxInput)
    httpxOut = response.text
    return httpxOut


def urllib_body(urllibInput):
    response = urllib.request.urlopen(urllibInput)
    urllibOut = response.read()
    return urllibOut


def urllib_partial(urllibPartialInput, urllibOtherInput):
    response = urllib.request.urlopen(urllibPartialInput)
    urllibPartialOut = response.read(4)
    other = urllib.request.urlopen(urllibOtherInput)
    urllibOtherOut = other.read()
    return urllibPartialOut, urllibOtherOut


def urllib_custom(urllibCustomInput, urllibCustomOpener):
    response = urllibCustomOpener.open(urllibCustomInput)
    urllibCustomOut = response.read()
    return urllibCustomOut
