const identity = item => sink(item);
function use(value: string) { return identity(value); }
const passthrough = value => value;
