import fastify from 'fastify';
const app = fastify();
function parameter(require: (path: string) => any) {
  app.register(require('./fake-parameter'), {prefix: '/fake-parameter'});
}
function local() {
  const require = (path: string) => ({path});
  app.register(require('./fake-local'), {prefix: '/fake-local'});
}
app.register(require('./real'), {prefix: '/real'});
