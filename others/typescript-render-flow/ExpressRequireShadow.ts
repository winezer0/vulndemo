function fake(require: (name: string) => any) {
  const app = require('express')();
  app.get('/fake', handler);
}
const real = require('express')();
real.get('/real', handler);
