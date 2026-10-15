class AlphaField { value = 1; }
class BetaField { value = 2; }
export function readField(kind: number) {
  let item: AlphaField = new AlphaField();
  switch (kind) {
    case 0: let item: BetaField = new BetaField(); item.value; break;
    default: item.value;
  }
  item.value;
}
