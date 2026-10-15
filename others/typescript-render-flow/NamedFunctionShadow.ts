import * as api from "./lib";
export function host() {
  const wrapper = function api() { api["run"](); };
  api["run"]();
  return wrapper;
}
