const appRouter = createTRPCRouter({user: publicProcedure.query(() => 1)});
const firstConsumer = trpc.appRouter.user.useQuery();
const secondConsumer = trpc.appRouter.user.useQuery();
