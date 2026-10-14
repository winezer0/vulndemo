from flask import Blueprint

first = Blueprint('first', __name__)
second = Blueprint('second', __name__)

@first.get('/items')
def first_items():
    return 'first'

@second.get('/items')
def second_items():
    return 'second'
