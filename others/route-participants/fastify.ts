import fastify from 'fastify';
const app = fastify();
function auth(request: any, reply: any, done: any) { done(); }
function serve(request: any, reply: any) { return reply.send('ok'); }
const dynamic = [];
app.route({method: 'GET', url: '/ts', preHandler: [auth, auth, ...dynamic], handler: serve});
