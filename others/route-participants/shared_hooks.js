export function sharedAuth(request, reply, done) { done(); }
export function sharedServe(request, reply) { return reply.send('ok'); }
