import fastify from 'fastify';

const app = fastify();
declare const options: {url: string; method: 'GET'};
declare function handler(): void;

app.route({url: '/overridden', method: 'GET', ...options, handler});
app.route({...options, url: '/ordered', method: 'PATCH', handler});
app.get('/us\\u0065rs', handler);
