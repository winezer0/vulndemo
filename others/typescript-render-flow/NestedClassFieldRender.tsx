export class NestedFieldHeader {}
export class NestedFieldPage {
  render = function() {
    return (() => <NestedFieldHeader />)();
  };
}
