import fastify from 'fastify';
import { forwardedAuth as importedAuth, sharedServe as importedServe } from './shared_barrel';
const app = fastify();
app.route({method: 'GET', url: '/cross-file', preHandler: [importedAuth], handler: importedServe});
