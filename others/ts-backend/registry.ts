export interface Service {
  run(value: string): void;
}

export class ServiceRegistry {
  private services: Record<string, Service> = {};

  register(name: string, service: Service): void {
    this.services[name] = service;
  }

  dispatch(name: string, value: string): void {
    const service: Service = this.services[name];
    service["run"](value);
  }
}
