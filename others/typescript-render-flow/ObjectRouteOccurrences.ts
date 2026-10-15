import fastify from 'fastify';
import Hapi from '@hapi/hapi';

const app = fastify();
const server = Hapi.server();
function firstObjectHandler() {}
function secondObjectHandler() {}
function thirdObjectHandler() {}

app.route({method: ['GET', 'POST'], url: '/object-occurrences', handler: firstObjectHandler});
app.route({method: 'GET', url: '/object-occurrences', handler: secondObjectHandler});
app.get('/object-occurrences', thirdObjectHandler);
server.route({method: ['GET', 'POST'], path: '/object-occurrences', handler: firstObjectHandler});
server.route({method: 'GET', path: '/object-occurrences', handler: secondObjectHandler});
