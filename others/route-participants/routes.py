from fastapi import FastAPI

app = FastAPI()

@app.get('/python')
def serve():
    return 'ok'
