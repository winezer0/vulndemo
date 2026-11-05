// Await applies to the selected call result, not to every nested computation.
async function awaitBoundaries() {
  const direct = await directSource();
  const wrapped = await (wrappedSource());
  const pending = pendingSource();
  const nested = await consume(nestedSource());
  const chained = await innerSource().load();
  return [direct, wrapped, pending, nested, chained];
}
