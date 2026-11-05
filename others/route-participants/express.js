const express = require('express');
const app = express();
function auth(req, res, next) { next(); }
function serve(req, res) { res.send('ok'); }
app.get('/express-js', auth, serve);
