import * as api from "./lib";
export function host() {
  const Box = class api { static run() { api["run"](); } };
  api["run"]();
  { class api { static run() {} } api["run"](); }
  api["run"]();
  return Box;
}
