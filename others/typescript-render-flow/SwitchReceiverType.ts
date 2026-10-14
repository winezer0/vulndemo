class SwitchAlpha { switchRun(): void {} }
class SwitchBeta { switchRun(): void {} }
export function use(kind: number) {
  let item: SwitchAlpha = new SwitchAlpha();
  switch (kind) {
    case 0: let item: SwitchBeta = new SwitchBeta(); item.switchRun(); break;
    default: item.switchRun();
  }
  item.switchRun();
}
