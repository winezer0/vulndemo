import { Service } from "./registry";

export class ConsoleService implements Service {
  run(value: string): void {
    console.log(value);
  }
}

export class AuditService implements Service {
  run(value: string): void {
    console.log(`audit:${value}`);
  }
}

export class UnrelatedService {
  run(): void {
    console.log("unrelated");
  }
}
