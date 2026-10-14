import fastify from 'fastify';

const app = fastify();
app.route({
  // url: '/fake', method: 'DELETE'
  url: '/objects', method: ['GET', 'POST'], handler: listObjects,
});
app.route({ url: dynamicPath, method: 'GET', handler: listObjects });
app.route({ url: '/dynamic-method', method: dynamicMethod, handler: listObjects });
app.route(dynamicRoute, { url: '/second-argument', method: 'DELETE', handler: listObjects });
