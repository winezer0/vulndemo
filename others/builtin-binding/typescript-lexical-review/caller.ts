parseInt('1'); // lexical:absent
const object = {
  [parseInt('1')](parseInt) {} // lexical:absent
};
class Container {
  value = parseInt('1'); // lexical:unknown
  static [parseInt('1')](parseInt) {} // lexical:absent
}
(parseInt) = value => value;
parseInt('1'); // lexical:unknown
