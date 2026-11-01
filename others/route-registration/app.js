const express = require('express');
const firstRouter = express.Router();
const secondRouter = express.Router();
function first(req, res) { return res.send('first'); }
function second(req, res) { return res.send('second'); }
firstRouter.get('/same', first);
secondRouter.get('/same', second);
