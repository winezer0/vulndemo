const users = createTRPCRouter({list: publicProcedure.query(() => [])});
const admin = createTRPCRouter({remove: publicProcedure.mutation(() => true)});
const list = trpc.users.list.useQuery();
const other = {fake: foo.query(() => 1)};
const key = 'computed';
const settings = createTRPCRouter({
  [key]: publicProcedure.query(() => true),
  'static': publicProcedure.query(() => true),
  'sta\x74ic': publicProcedure.query(() => true),
});
