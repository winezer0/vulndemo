from flask import Flask
app = Flask(__name__)
@app.route('/flask')
def serve():
    return 'ok'
