import { Service as AService } from "./a";

export function dispatch(value: string): void {
  AService["run"](value);
}
