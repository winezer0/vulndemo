export default async function(fastify: FastifyInstance) {
  fastify.get('/items', listItems);
}
