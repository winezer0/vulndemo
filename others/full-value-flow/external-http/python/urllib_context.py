import urllib.request


def urllib_context(urllibContextInput, urllibContextOtherInput):
    with urllib.request.urlopen(urllibContextInput) as response:
        urllibContextOut = response.read()
    with urllib.request.urlopen(urllibContextOtherInput) as other:
        urllibContextOtherOut = other.read()
    return urllibContextOut, urllibContextOtherOut


def urllib_context_custom(urllibContextCustomInput, urllibContextOpener):
    with urllibContextOpener.open(urllibContextCustomInput) as response:
        urllibContextCustomOut = response.read()
    return urllibContextCustomOut
