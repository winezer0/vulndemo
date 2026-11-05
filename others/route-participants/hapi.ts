import Hapi from '@hapi/hapi';
const server = Hapi.server();
function serve() { return 'ok'; }
server.route({method: 'GET', path: '/hapi', handler: serve});
