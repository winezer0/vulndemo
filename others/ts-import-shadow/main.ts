import * as service from "./service";

export function dispatch(service: Record<string, (value: string) => void>, value: string): void {
  service["run"](value);
}
