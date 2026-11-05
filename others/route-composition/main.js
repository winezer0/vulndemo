const express = require('express');
const mounted = require('./routes');
const app = express();
function globalAuth(req, res, next) { next(); }
app.use(globalAuth);
app.use('/api', mounted);
