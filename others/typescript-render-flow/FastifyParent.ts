import fastify from 'fastify';
import plugin from './FastifyPlugin';

const app = fastify();
app.register(plugin, { prefix: '/api' });
// app.get('/commented', parentHandler);
const note = "app.post('/string', parentHandler)";
app.get('/parent', parentHandler);
app.post(dynamicPath, parentHandler);
