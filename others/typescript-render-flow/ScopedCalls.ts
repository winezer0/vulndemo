class Alpha { run(): void {} }
class Beta { run(): void {} }

function firstCall() {
  const item: Alpha = new Alpha();
  item.run();
}

function secondCall() {
  const item: Beta = new Beta();
  item.run();
}

function conditionalCall(flag: boolean) {
  let item: Alpha = new Alpha();
  if (flag) { item = new Beta() as any; }
  item.run();
}

function shadowedCall() {
  let item: Alpha = new Alpha();
  { let item: Beta = new Beta(); item = new Beta(); item.run(); }
  item.run();
}

function immediateWrite() {
  let item: Alpha = new Alpha();
  (() => { item = new Beta() as any; })();
  item.run();
}

function namedWrite() {
  let item: Alpha = new Alpha();
  const change = () => { item = new Beta() as any; };
  change();
  item.run();
}

function conditionalNamedWrite(flag: boolean) {
  let item: Alpha = new Alpha();
  const change = () => { item = new Beta() as any; };
  if (flag) { change(); }
  item.run();
}

function aliasedWrite(flag: boolean) {
  let item: Alpha = new Alpha();
  const change = () => { item = new Beta() as any; };
  const alias = change;
  if (flag) { alias(); }
  item.run();
}
