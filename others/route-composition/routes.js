const express = require('express');
const router = express.Router();
function localAuth(req, res, next) { next(); }
function serve(req, res) { res.send('ok'); }
router.use(localAuth);
router.get('/items', serve);
module.exports = router;
