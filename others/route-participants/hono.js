import { Hono } from 'hono';
const app = new Hono();
function serve(c) { return c.text('ok'); }
app.get('/hono', serve);
