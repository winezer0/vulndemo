from flask import Flask

app = Flask(__name__)

@app.before_request
def flask_auth():
    pass

@app.get('/py')
def users():
    return 'ok'
