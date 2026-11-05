import express from 'express';
const app = express();
function serve(req: any, res: any) { res.send('ok'); }
app.get('/express-ts', serve);
