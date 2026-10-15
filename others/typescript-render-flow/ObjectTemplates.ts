import fastify from 'fastify';
import Hapi from '@hapi/hapi';

const app = fastify();
const server = Hapi.server();
app.route({ url: `/fastify-template`, method: `GET`, handler });
server.route({ path: `/hapi-template`, method: `POST`, handler });
app.route({ url: `/dynamic/${id}`, method: 'GET', handler });
server.route({ path: `/dynamic/${id}`, method: 'POST', handler });
