const app = require('fastify')();
function auth(request, reply, done) { done(); }
function serve(request, reply) { return reply.send('ok'); }
const dynamic = [];
app.route({method: 'GET', url: '/js', preHandler: [auth, auth, ...dynamic], handler: serve});
