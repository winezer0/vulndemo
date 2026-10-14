class FieldAlpha { run(): void {} }
class FieldBeta { run(): void {} }

class FieldHolder {
  service: FieldAlpha = new FieldAlpha();
  use(flag: boolean) {
    this.service.run();
    if (flag) { this.service = new FieldBeta() as any; }
    this.service.run();
  }
}
