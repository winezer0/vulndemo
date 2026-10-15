function run(obj, key) {
  obj[key]();
  obj['go']();
}
const { readFile: read, writeFile } = require('fs');
read();
writeFile();
function sink(value) { return value; }
const echo = value => sink(value);
class Box {}
function makeBox() { return new Box; }
