class FieldInstanceService { run(): void {} }
class FieldStaticService { run(): void {} }
const fieldSource = 1;
export class FieldArrowHolder {
  service: FieldInstanceService = new FieldInstanceService();
  static service: FieldStaticService = new FieldStaticService();
  run = () => { this.service.run(); return fieldSource; };
  static runStatic = () => this.service.run();
}
