import Hapi, { otherFactory } from '@hapi/hapi';

const server = Hapi.server();
server.route({
  // path: '/fake', method: 'DELETE'
  path: '/hapi-objects', method: ['GET', 'POST'], handler: listObjects,
});
server.route({ path: dynamicPath, method: 'GET', handler: listObjects });
server.route({ path: '/dynamic-method', method: dynamicMethod, handler: listObjects });
server.route(dynamicRoute, { path: '/second-argument', method: 'DELETE', handler: listObjects });
const shadow = otherFactory.server();
shadow.route({ path: '/named-import', method: 'DELETE', handler: listObjects });
