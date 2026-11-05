const express = require('express');
const app = express();
function auth(req, res, next) { next(); }
function serve(req, res) { res.send('ok'); }
app.use('/admin', auth);
app.get('/public', serve);
app.get('/admin/users', serve);
