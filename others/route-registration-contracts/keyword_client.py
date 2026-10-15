from keyword_api import accept_keywords
def run_imported(x, y, extra):
    accept_keywords(required=x)
    accept_keywords(optional=y, required=x)
    accept_keywords(required=x+y)
    accept_keywords(required=x, **extra)
